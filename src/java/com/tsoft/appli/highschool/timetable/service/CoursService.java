/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.timetable.service;

import com.tsoft.appli.highschool.service.AnneeService;
import com.tsoft.appli.highschool.timetable.model.Cours;
import com.tsoft.dao.hibernate.service.HibernateService;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class CoursService extends HibernateService<Cours> {

    @Autowired
    AnneeService as;

    @Override
    public Cours create(final Cours entity) throws Exception {
        List<Criterion> criteres = new ArrayList();
        criteres.add(Restrictions.eq("annee", as.getAnneeCourante()));
        criteres.add(Restrictions.eq("classe", entity.getClasse()));
        criteres.add(Restrictions.eq("matiere", entity.getMatiere()));
        Cours cc = getOne(Cours.class, criteres, null);
        if (cc != null) {
            throw new Exception(entity.getMatiere() + " est deja donne en " + entity.getClasse().getLibelle());
        }
        entity.setAnnee(as.getAnneeCourante());
        return super.create(entity);
    }

    @Override
    public void saveOrUpdate(final Cours entity) throws Exception {
        List<Criterion> criteres = new ArrayList();
        criteres.add(Restrictions.eq("annee", as.getAnneeCourante()));
        criteres.add(Restrictions.eq("classe", entity.getClasse()));
        criteres.add(Restrictions.eq("matiere", entity.getMatiere()));
        Cours cc = getOne(Cours.class, criteres, null);
        if (cc != null) {
            throw new Exception(entity.getMatiere() + " est deja donne en " + entity.getClasse().getLibelle());
        }
        entity.setAnnee(as.getAnneeCourante());
        super.saveOrUpdate(entity);

    }

}
