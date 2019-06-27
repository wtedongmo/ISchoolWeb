/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.exams.service;

import com.tsoft.appli.highschool.exams.model.Coefficient;
import com.tsoft.appli.highschool.exams.model.Notes;
import com.tsoft.appli.highschool.exams.model.Sequence;
import com.tsoft.appli.highschool.model.EleveInscrit;
import com.tsoft.appli.highschool.service.AnneeService;
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
public class NotesController extends EmptyCustomActionProcess {

    @Autowired
    AnneeService as;

    @RequestMapping(value = "/matiereclasse/{classe}",
            method = RequestMethod.GET)
    public List retrieveclasses(@PathVariable int classe) throws Exception {
        String query = "select m.code,m.libelle from Matiere m join Coefficient coef "
                + "on (m.code=coef.code_matiere) join Classe c on (c.code_serie=coef.code_serie and c.code=" + classe + ") ";
        return sqc.retrieveQuery(query);
    }

    @RequestMapping(value = "/notes/data/{classe}/{matiere}/{seq}",
            method = RequestMethod.GET)
    public List getdata(@PathVariable int classe,
            @PathVariable int matiere, @PathVariable int seq) throws Exception {
        String query = "select ei.code  as eleve,n.code,coef.code as "
                + "coef,(concat(e.code,concat('E',date_format(e.date_creation,'%Y')))) as matricule,e.nom_prenom,ifnull(n.note,0) as note,n.cycle_vie "
                + "from EleveInscrit ei  join Classe c on "
                + "(c.code=ei.code_classe and "
                + "c.code=" + classe + " "
                + " and ei.code_annee = " + as.getAnneeCourante().getCode() + ") join Coefficient coef  "
                + "on (coef.code_serie =c.code_serie) join Matiere m"
                + " on (coef.code_matiere=m.code and "
                + "m.code=" + matiere + ") "
                + "join Eleve e on(ei.code_eleve=e.code) left "
                + "join Notes n on (n.code_coefficient=coef.code "
                + "and n.code_eleveinscrit=ei.code "
                + "and n.code_sequence=" + seq + ")";

        return sqc.retrieveQuery(query);
    }

    @RequestMapping(value = "/notes/data/{classe}/{matiere}/{seq}",
            method = RequestMethod.POST)
    public void postdata(
            @PathVariable int classe,
            @PathVariable int matiere, @PathVariable int seq,
            HttpSession session, HttpServletRequest request,
            @RequestBody NotesDTOList listnotesDTO) throws Exception {

        List<Notes> listnotes = new ArrayList();
        for (NotesDTO ndto : listnotesDTO.getNotes()) {
            if (ndto.getNote() != 0) {
                Notes n = new Notes();
                n.setCode(ndto.getCode());
                n.setNote(ndto.getNote());
                n.setNumero_sequence((Sequence) hibernateService.getById(seq, Sequence.class));
                n.setCoefficient((Coefficient) hibernateService.getById(ndto.getCoef(), Coefficient.class));
                n.setEleveinscrit((EleveInscrit) hibernateService.getById(ndto.getEleve(), EleveInscrit.class));
                listnotes.add(n);
            }
        }

        hibernateService.saveAll(listnotes);
        session.setAttribute("list" + Notes.class.getSimpleName(), listnotes);

    }

    @Override
    public String form() throws Exception {
        return "tpl/highschool/notes.html";
    }

    @Override
    public String libelle() {
        return "Saisie des notes";
    }

    @Override
    public Class rubrique() throws Exception {
        return  Notes.class;
    }
}
