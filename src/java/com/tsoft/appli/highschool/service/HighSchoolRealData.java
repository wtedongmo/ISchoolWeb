/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.service;

import com.tsoft.appli.highschool.exams.model.Sequence;
import com.tsoft.appli.highschool.model.AnneeScolaire;
import com.tsoft.appli.highschool.model.Matiere;
import com.tsoft.appli.highschool.model.Niveau;
import com.tsoft.appli.highschool.model.Serie;
import com.tsoft.appli.highschool.model.TypeMatiere;
import com.tsoft.appli.highschool.timetable.model.Creneau;
import com.tsoft.appli.highschool.timetable.model.Jour;
import com.tsoft.dao.hibernate.service.HibernateServiceWrapper;
import com.tsoft.utils.DateUtils;
import com.tsoft.utils.enumerations.DataLifeCycle;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class HighSchoolRealData implements com.tsoft.web.init.RealData{

    @Autowired
    HibernateServiceWrapper service;

    public void createMatiere(List<String> Matieres, TypeMatiere tm, List tosave) {
        for (String ss : Matieres) {
            Matiere m = new Matiere();
            m.setLibelle(ss);
            m.setType(tm);
            tosave.add(m);
        }
    }

    @Override
    public void populateData(HttpServletRequest req) throws Exception {
        //insert real data

        List tosave = new ArrayList();
        DataFactory df = new DataFactory();

        AnneeScolaire as = new AnneeScolaire();
        as.setDate_debut(df.getDate(2014, 1, 1));
        as.setDate_fin(df.getDate(2015, 1, 1));
        as.setCycle_vie(DataLifeCycle.ACTIF);
        tosave.add(as);

        LocalTime timee = LocalTime.of(06, 30);
        for (int i = 0; i < 9; i++) {
            Creneau cr = new Creneau();
            cr.setHeure_debut(DateUtils.getDateFromTime(timee));
            if (i == 2) {
                timee = timee.plusMinutes(15);
            } else {
                if (i == 6) {
                    timee = timee.plusMinutes(30);
                } else {
                    timee = timee.plusMinutes(55);
                }
            }
            cr.setHeure_fin(DateUtils.getDateFromTime(timee));
            tosave.add(cr);

        }

        for (int i = 0; i < 6; i++) {
            Sequence s = new Sequence();
            tosave.add(s);
        }

        TypeMatiere tm1 = new TypeMatiere();
        tm1.setLibelle("MATIERES SCIENTIFIQUES");
        tosave.add(tm1);
        List<String> matieresScientiques = new ArrayList();
        matieresScientiques.add("Mathematique");
        matieresScientiques.add("Physique");
        matieresScientiques.add("Informatique");
        matieresScientiques.add("PCT");
        matieresScientiques.add("SVT");
        createMatiere(matieresScientiques, tm1, tosave);
        TypeMatiere tm2 = new TypeMatiere();
        tm2.setLibelle("MATIERES LITTERAIRES");
        tosave.add(tm2);
        List<String> matieresLitteraires = new ArrayList();
        matieresLitteraires.add("Francais");
        matieresLitteraires.add("Anglais");
        matieresLitteraires.add("Redaction");
        matieresLitteraires.add("Allemand");
        matieresLitteraires.add("Espagnol");
        matieresLitteraires.add("Litterature");
        matieresLitteraires.add("Philosophie");
        matieresLitteraires.add("Etude de Texte");
        matieresLitteraires.add("Histoire");
        matieresLitteraires.add("Geographie");
        createMatiere(matieresLitteraires, tm2, tosave);
        TypeMatiere tm3 = new TypeMatiere();
        tm3.setLibelle("MATIERES FORMATIONS HUMAINES");
        tosave.add(tm3);
        List<String> matieressociales = new ArrayList();
        matieressociales.add("Education Vie Et Amour");
        matieressociales.add("ESF");
        matieressociales.add("Travail Manuel");
        matieressociales.add("Education a la Citoyennete");
        createMatiere(matieressociales, tm3, tosave);

        Map<String, Niveau> series = new HashMap();
        series.put("Sixieme_6M", Niveau.NIVEAU1);
        series.put("Cinquieme_5M", Niveau.NIVEAU2);
        series.put("Quatrieme Espagnol_4ESP", Niveau.NIVEAU3);
        series.put("Quatrieme Allemand_4ALL", Niveau.NIVEAU3);
        series.put("Troisieme Espagnol_3ESP", Niveau.NIVEAU4);
        series.put("Troisieme Allemand_3ALL", Niveau.NIVEAU4);
        series.put("Seconde Espagnol_2ESP", Niveau.NIVEAU5);
        series.put("Seconde Allemand_2ALL", Niveau.NIVEAU5);
        series.put("Seconde C_2C", Niveau.NIVEAU5);
        series.put("Premiere Espagnol_PESP", Niveau.NIVEAU6);
        series.put("Premiere Allemand_PALL", Niveau.NIVEAU6);
        series.put("Premiere C_PC", Niveau.NIVEAU6);
        series.put("Premiere D_PD", Niveau.NIVEAU6);
        series.put("Terminale Espagnol_TESP", Niveau.NIVEAU7);
        series.put("Terminale Allemand_TALL", Niveau.NIVEAU7);
        series.put("Terminale C_TC", Niveau.NIVEAU7);
        series.put("Terminale D_TD", Niveau.NIVEAU7);

        for (Map.Entry<String, Niveau> serie : series.entrySet()) {
            Serie s = new Serie();
            s.setLibelle(serie.getKey().split("_")[0]);
            s.setAbreviation(serie.getKey().split("_")[1]);
            s.setNiveau(serie.getValue());
            s.setInscription(new BigDecimal("10000"));
            s.setInscription_new(new BigDecimal("10000"));
            s.setTranche1(new BigDecimal("50000"));
            s.setTranche1_new(new BigDecimal("50000"));
            tosave.add(s);

        }

        Jour j = new Jour();
        j.setCode(1);
        j.setLibelle("Lundi");
        tosave.add(j);
        j = new Jour();
         j.setCode(2);
        j.setLibelle("Mardi");
        tosave.add(j);
        j = new Jour();
         j.setCode(3);
        j.setLibelle("Mercredi");
        tosave.add(j);
        j = new Jour();
         j.setCode(4);
        j.setLibelle("Jeudi");
        tosave.add(j);
        j = new Jour();
         j.setCode(5);
        j.setLibelle("Vendredi");
        tosave.add(j);

        service.saveAll(tosave);

    }

}
