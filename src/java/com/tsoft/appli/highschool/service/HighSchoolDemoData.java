/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.service;

import com.tsoft.service.process.EmptyActionProcess;
import com.tsoft.web.init.DemoData;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class HighSchoolDemoData implements DemoData{

    @Override
    public void populateData(HttpServletRequest req) throws Exception {
        // test  data 
        DataFactory df = new DataFactory();

        //creation de 2 professeurs par matiere
        Date minDate = df.getDate(1980, 1, 1);
        Date maxDate = df.getDate(1950, 1, 1);
//        List<Matiere> matieres = hibernateService.getAll(Matiere.class);
//        for (Matiere m : matieres) {
//            for (int i = 0; i < 2; i++) {
//                Professeur u = new Professeur();
//                u.setCivilite(i % 2 == 0 ? Civilite.M : Civilite.MME);
//                u.setNom_prenom(df.getName());
//                u.setDateNaissance((df.getDateBetween(minDate, maxDate)));
//                u.setEmailAdresse(df.getEmailAddress());
//                u.setTelephoneMobile("237 " + df.getNumberText(8));
//                u.setLieuNaissance(df.getCity());
//                u.setNumero_cni(Integer.parseInt(df.getNumberText(8)));
//                u.setMatiere(m);
//                hibernateService.create(u);
//
//            }
//        }

        //creation de 2 classe par serie
//        List<Serie> series = hibernateService.getAll(Serie.class);
//        for (Serie s : series) {
//            for (int i = 0; i < 2; i++) {
//                Classe c = new Classe();
//                c.setCodeSerie(s);
//                hibernateService.create(c);
//            }
//        }
//        minDate = df.getDate(2005, 1, 1);
//        maxDate = df.getDate(1996, 1, 1);
//
//        for (int i = 0; i < 100; i++) {
//            Eleve e = new Eleve();
//            e.setCivilite(i % 2 == 0 ? Civilite.M : Civilite.MLLE);
//            e.setNom_prenom(df.getName());
//            e.setDateNaissance((df.getDateBetween(minDate, maxDate)));
//            e.setEmailAdresse(df.getEmailAddress());
//            e.setTelephoneMobile("237 " + df.getNumberText(8));
//            e.setLieuNaissance(df.getCity());
//            e.setNom_mere(df.getName());
//            e.setNom_pere(df.getName());
//            e.setTelephone_pere("237 " + df.getNumberText(8));
//            e.setTelephone_mere("237 " + df.getNumberText(8));
//            hibernateService.create(e);
//
//        }
//        LocalTime timee = LocalTime.of(07, 30);
//        for (int i = 0; i < 9; i++) {
//            Creneau cr = new Creneau();
//            cr.setHeure_debut(DateUtils.getDateFromTime(timee));
//            if (i == 2) {
//                timee = timee.plusMinutes(15);
//            } else {
//                if (i == 6) {
//                    timee = timee.plusMinutes(45);
//                } else {
//                    timee = timee.plusHours(1);
//                }
//            }
//            cr.setHeure_fin(DateUtils.getDateFromTime(timee));
//            hibernateService.create(cr);
//
//        }
       
    }

}
