/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.service;

import com.tsoft.appli.highschool.model.Classe;
import com.tsoft.appli.highschool.model.Ecole;
import com.tsoft.appli.highschool.model.Eleve;
import com.tsoft.appli.highschool.model.EleveInscrit;
import com.tsoft.exceptions.BusinessException;
import com.tsoft.service.process.EmptyReportProcess;
import com.tsoft.utils.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tchipi
 */
@RestController
@RequestMapping("/app")
public class CarteScolaire extends EmptyReportProcess {

    @JoinColumn
    @NotNull
    private Eleve eleve;

    @Autowired
    AnneeService as;

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    @Override
    public String libelle() {
        return "Carte Scolaire Eleve";
    }

    @Override
    public Object run(HttpSession session, HttpServletRequest request) throws Exception {
        List<Criterion> criteres = new ArrayList();
        criteres.add(Restrictions.eq("eleve", getEleve()));
        criteres.add(Restrictions.eq("annee", as.getAnneeCourante()));
        EleveInscrit ei = (EleveInscrit) hibernateService.getOne(EleveInscrit.class, criteres, null);
        String reportfile = "";
        //remplissage des parametres du report
        Map params = new HashMap();
        reportfile = "classpath:com/tsoft/appli/highschool/service/CarteScolaire.jasper";
        //recuperation de la classe
        params.put("code_eleve", getEleve().getCode());
        params.put("annee", as.getAnneeCourante().getLibelle());
        params.put("code_annee", as.getAnneeCourante().getCode());
        //information about school
        Ecole ecole = (Ecole) hibernateService.getOne(Ecole.class, "select e from Ecole e");
        if (ecole == null) {
            throw new BusinessException("Paramètres de l'ecole non definis");
        }
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoite_postale() + " Tel:" + ecole.getTelephoneMobile());
        params.put("logo_ecole", FileUtils.getUploadedFile(ecole.getLogo()).getAbsolutePath());
        //determination du chemin des subreports
        params.put("SUBREPORT_DIR", resourceLoader.getResource(reportfile).getFile().getParent() + File.separator);
        params.put("cmr", resourceLoader.getResource("classpath:com/tsoft/appli/highschool/service/Cameroun.jpg").getFile().getAbsolutePath());

        params.put("upload_dir", FileUtils.getUploadedDir());

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "Carte"
                + getEleve().getMatricule() + ".pdf";
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream() //file jasper
                , params, //params report
                dataSource.getConnection());  //datasource

        JasperExportManager.exportReportToPdfFile(jp, destfile);

        return download(destfile);
    }

    @Override
    public Class rubrique() throws Exception {
        return Classe.class;
    }

}
