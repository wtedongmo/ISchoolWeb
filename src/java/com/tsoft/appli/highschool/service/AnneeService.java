/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.service;

import com.tsoft.appli.highschool.model.AnneeScolaire;
import com.tsoft.dao.hibernate.service.HibernateService;
import com.tsoft.exceptions.BusinessException;
import com.tsoft.utils.enumerations.DataLifeCycle;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class AnneeService extends HibernateService<AnneeScolaire> {

    public AnneeScolaire getAnneeCourante() throws Exception {
        List<Criterion> criteres = new ArrayList();
        criteres.add(Restrictions.eq("cycle_vie", DataLifeCycle.ACTIF));
        AnneeScolaire as = getOne(AnneeScolaire.class, criteres, null);
        if (as == null) {
            throw new BusinessException("Annee courante non definie");
        }
        return as;
    }
    
    

}
