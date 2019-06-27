/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.finances.service;

import com.tsoft.appli.highschool.finances.model.Caisse;
import com.tsoft.dao.hibernate.service.HibernateService;
import com.tsoft.exceptions.BusinessException;
import com.tsoft.security.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class CaisseService extends HibernateService<Caisse> {

    public Caisse getCaisse() throws Exception {
        User u=((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Caisse c = getOne(Caisse.class, "select c from Caisse c  where gerant.code= "+u.getCode());
        if (c == null) {
            throw new BusinessException("Aucune Caisse associé à ce gerant");
//            c = new Caisse();
//            c.setEtat_caisse(EtatCaisse.OUVERT);
//            c.setLibelle("Caisse 1");
//            c.setGerant(u);
//            c.setSolde_reel(BigDecimal.ZERO);
//            create(c);
        }
        return c;
    }
}
