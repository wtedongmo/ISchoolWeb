/*
 * Reglemento change this license header, choose License Headers in Project Properties.
 * Reglemento change this template file, choose Reglementools | Reglementemplates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.finances.service;

import com.tsoft.appli.highschool.finances.model.EntreeCaisse;
import com.tsoft.appli.highschool.finances.model.Reglement;
import com.tsoft.dao.hibernate.service.HibernateService;
import com.tsoft.dao.hibernate.service.HibernateServiceWrapper;
import com.tsoft.exceptions.BusinessException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class ReglementService extends HibernateService<Reglement> {

    @Autowired
    CaisseService cs;
    @Autowired
    HibernateServiceWrapper mvtservice;

    @Override
    public Reglement create(final Reglement r) throws Exception {
        EntreeCaisse ec = new EntreeCaisse();
        ec.setCaisse(cs.getCaisse());
        ec.setDate_mouvement(new Date());
        ec.setMotif("Versement " + r.getEleveinscrit().getEleve().getNom_prenom());
        ec.setMontant(r.getMontant());
        ec.setObjet_Mouvement(r.getObjet());
        mvtservice.create(ec);
        super.create(r);
        return r;
    }
    
    @Override
    public Reglement update(final Reglement entity) throws Exception {
        throw  new BusinessException("Mise à jour impossible");
    }

}
