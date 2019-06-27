/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.finances.service;

import com.tsoft.annotations.form.Fichier;
import com.tsoft.annotations.form.Libelle;
import com.tsoft.appli.highschool.finances.model.ObjectMvtCaisse;
import com.tsoft.appli.highschool.finances.model.Reglement;
import com.tsoft.appli.highschool.model.Ecole;
import com.tsoft.appli.highschool.model.Eleve;
import com.tsoft.appli.highschool.model.EleveInscrit;
import com.tsoft.appli.highschool.service.AnneeService;
import com.tsoft.exceptions.BusinessException;
import com.tsoft.service.process.EmptyReportProcess;
import com.tsoft.utils.FileUtils;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.hibernate.annotations.Formula;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
@Scope("session")
public class RecuVersement extends EmptyReportProcess {

    @JoinColumn
    @NotNull
    private Eleve eleve;
    @Enumerated(EnumType.STRING)
    private ObjectMvtCaisse objet;
    @NotNull
    private BigDecimal montant;
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_reglement;
    
//    @NotNull
//    @Formula("(concat(eleve,concat('/M/',date_format(date_reglement,'%m/%Y'))))")
//    private String reference;
    
    @Fichier
    private String file_reference;
    

    @Autowired
    AnneeService as;

    @Override
    public void afterPropertiesSet() throws Exception {
        date_reglement = new Date();
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public ObjectMvtCaisse getObjet() {
        return objet;
    }

    public void setObjet(ObjectMvtCaisse objet) {
        this.objet = objet;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
    
    public String getFile_reference() {
        return file_reference;
    }

    public void setFile_reference(String file_reference) {
        this.file_reference = file_reference;
    }

    @Override
    public String libelle() {
        return "Effectuer Versement";
    }

    public Date getDate_reglement() {
        return date_reglement;
    }

    public void setDate_reglement(Date date_reglement) {
        this.date_reglement = date_reglement;
    }

    @Override
    public Object run(HttpSession session, HttpServletRequest request) throws Exception {
        List<Criterion> criteres = new ArrayList();
        criteres.add(Restrictions.eq("eleve", getEleve()));
        criteres.add(Restrictions.eq("date_reglement", getDate_reglement()));
        criteres.add(Restrictions.eq("objet", getObjet()));
        Reglement r = (Reglement) hibernateService.getOne(Reglement.class, criteres, null);
        if(r!=null) 
            return null;
//             throw new BusinessException("Reglement en double");
        criteres.remove(2);
        criteres.remove(1);
        
        criteres.add(Restrictions.eq("annee", as.getAnneeCourante()));
        EleveInscrit ei = (EleveInscrit) hibernateService.getOne(EleveInscrit.class, criteres, null);

        
        r = new Reglement();
        r.setEleve(getEleve());
        r.setEleveinscrit(ei);
        r.setMontant(getMontant());
        r.setObjet(getObjet());
        r.setFile_reference(getFile_reference());
        
        Date date_reg = getDate_reglement();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date_reg);
        cal.add(Calendar.HOUR, -1);
        date_reg = cal.getTime();
//                
        r.setDate_reglement(date_reg);
        hibernateService.create(r);

        //impression Recu
        String reportfile = "";
        //remplissage des parametres du report
        Map params = new HashMap();
        reportfile = "classpath:com/tsoft/appli/highschool/finances/service/RecuVersement.jasper";

        params.put("code_versement", r.getCode());
        params.put("code_eleve", getEleve().getCode());
        params.put("annee", as.getAnneeCourante().getLibelle());
        params.put("code_annee", as.getAnneeCourante().getCode());
        params.put("code_classe", ei.getClasse().getCode());
        //information about school
        Ecole ecole = (Ecole) hibernateService.getOne(Ecole.class, "select e from Ecole e");
        if (ecole == null) {
            throw new BusinessException("Paramètres de l'ecole non definis");
        }
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoite_postale() + " Tel:" + ecole.getTelephoneMobile());
        params.put("logo_ecole", FileUtils.getUploadedFile(ecole.getLogo()).getAbsolutePath());

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "RecuVersement"
                + r.getCode() + ".pdf";
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream() //file jasper
                , params, //params report
                dataSource.getConnection());  //datasource

        JasperExportManager.exportReportToPdfFile(jp, destfile);

        date_reglement = new Date();
        eleve=null;
        return download(destfile);

    }

    @Override
    public Class rubrique() throws Exception {
        return  Reglement.class;
    }

}
