/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.exams.service;

import com.tsoft.appli.highschool.exams.model.Coefficient;
import com.tsoft.appli.highschool.exams.model.Notes;
import com.tsoft.appli.highschool.model.Matiere;
import com.tsoft.appli.highschool.model.Serie;
import com.tsoft.service.process.EmptyCustomActionProcess;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class CoefficientController extends EmptyCustomActionProcess {

    @RequestMapping(value = "/coefs/{codematiere}",
            method = RequestMethod.GET)
    public List retrieveCoefsMatiere(@PathVariable int codematiere) throws Exception {
        String query = "select coef.code,s.code as codeSerie,s.libelle,ifnull(coef.valeur,0) as "
                + "valeur," + codematiere + " as codeMatiere from Serie s  left join  "
                + "Coefficient coef on (s.code=coef.code_serie and coef.code_matiere=" + codematiere + ")";
        return sqc.retrieveQuery(query);
    }

   
    @RequestMapping(value = "/postcoefs/{codematiere}",
            method = RequestMethod.POST)
    public void postCoefsMatiere(@PathVariable int codematiere,
            HttpServletRequest request, HttpSession session,
            @RequestBody ListCoefficientDTO listcoefsDTO
    ) throws Exception {
        List<Coefficient> listcoefs = new ArrayList();
        for (CoefficientDTO cdto : listcoefsDTO.getCoefs()) {
            if (cdto.getValeur() != null && !cdto.getValeur().equals(Byte.valueOf("0"))) {
                Coefficient c = new Coefficient();
                c.setCode(cdto.getCode());
                c.setValeur(cdto.getValeur());
                c.setCodeMatiere((Matiere) hibernateService.getById(cdto.getCodeMatiere(), Matiere.class));
                c.setCodeSerie((Serie) hibernateService.getById(cdto.getCodeSerie(), Serie.class));
                listcoefs.add(c);
            }
        }
        hibernateService.saveAll(listcoefs);
        session.setAttribute("list" + Coefficient.class.getSimpleName(), listcoefs);
    }

    @Override
    public String form() throws Exception {
        return "tpl/highschool/coefficient.html";
    }

    @Override
    public String libelle() {
        return "Definition Coefficient";
    }

    @Override
    public Class rubrique() throws Exception {
       return  Notes.class;
    }

}
