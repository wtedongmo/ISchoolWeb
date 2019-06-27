/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.finances.service;

import com.tsoft.appli.highschool.finances.model.Caisse;
import com.tsoft.appli.highschool.finances.model.EntreeCaisse;
import com.tsoft.appli.highschool.finances.model.EtatCaisse;
import com.tsoft.appli.highschool.finances.model.MouvementCaisse;
import com.tsoft.appli.highschool.finances.model.SortieCaisse;
import com.tsoft.dao.hibernate.service.HibernateService;
import com.tsoft.exceptions.BusinessException;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class MvtCaisseService extends HibernateService<MouvementCaisse> {

    @Override
    public MouvementCaisse create(final MouvementCaisse mvt) throws Exception {
        Caisse c = mvt.getCaisse();
        if (c == null) {
            throw new BusinessException("Aucune Caisse associée pour ce mouvement");
        }
        if (EtatCaisse.FERME.equals(c.getEtat_caisse())) {
            throw new BusinessException("Aucun Mouvement Possible car caisse fermée");
        }
        if (mvt instanceof EntreeCaisse) {
            mvt.setSolde(mvt.getMontant().add(c.getSolde_theorique()==null?BigDecimal.ZERO:c.getSolde_theorique()));
        }
        if (mvt instanceof SortieCaisse) {
            mvt.setSolde(mvt.getMontant().subtract(c.getSolde_theorique()==null?BigDecimal.ZERO:c.getSolde_theorique()).negate());
        }

        return super.create(mvt);
    }

}
