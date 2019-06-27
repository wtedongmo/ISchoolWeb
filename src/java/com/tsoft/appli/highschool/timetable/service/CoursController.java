/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.timetable.service;

import com.tsoft.appli.highschool.model.Classe;
import com.tsoft.appli.highschool.model.Professeur;
import com.tsoft.appli.highschool.service.AnneeService;
import com.tsoft.appli.highschool.timetable.model.Cours;
import com.tsoft.appli.highschool.timetable.model.Timetable;
import com.tsoft.service.process.EmptyCustomActionProcess;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tchipi
 */
@RestController
@RequestMapping("/app")
public class CoursController extends EmptyCustomActionProcess {
    
    @Autowired
    AnneeService as;

    @RequestMapping(value = "/cours/{codeprof}",
            method = RequestMethod.GET)
    public List retrieveData(@PathVariable int codeprof) throws Exception {
        String query = "select cc.code,c.code as classe,c.libelle,ifnull(cc.duree_heures,0) as duree_heures from Classe c left "
                + "join Cours cc on (c.code=cc.code_classe and cc.code_professeur="+codeprof+"  and cc.code_annee="+as.getAnneeCourante().getCode()+")";
        return sqc.retrieveQuery(query);
    }

    @RequestMapping(value = "/cours/{codeprof}",
            method = RequestMethod.POST)
    public void postData(@PathVariable int codeprof,
            HttpServletRequest request, HttpSession session,
            @RequestBody CoursDTOList listcoursDTO
    ) throws Exception {
        List<Cours> listcours = new ArrayList();
        for (CoursDTO cdto : listcoursDTO.getCourss()) {
            if (cdto.getDuree_heures() != null && !cdto.getDuree_heures().equals(Short.valueOf("0"))) {
                Cours c = new Cours();
                c.setCode(cdto.getCode());
                c.setDuree_heures(cdto.getDuree_heures());
                c.setProfesseur((Professeur) hibernateService.getById(codeprof, Professeur.class));
                c.setClasse((Classe) hibernateService.getById(cdto.getClasse(), Classe.class));
                c.setAnnee(as.getAnneeCourante());
                listcours.add(c);
            }
        }
        hibernateService.saveAll(listcours);
        session.setAttribute("list" + Cours.class.getSimpleName(), listcours);
    }

    @Override
    public String form() throws Exception {
        return "tpl/highschool/cours.html";
    }

    @Override
    public String libelle() {
        return "Affectation Classe aux Professeurs";
    }

    @Override
    public Class rubrique() throws Exception {
        return  Timetable.class;
    }

}
