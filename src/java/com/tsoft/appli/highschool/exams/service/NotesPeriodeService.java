/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.exams.service;

import com.tsoft.appli.highschool.exams.model.NotesPeriode;
import com.tsoft.dao.hibernate.service.HibernateService;
import com.tsoft.exceptions.BusinessException;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class NotesPeriodeService  extends HibernateService<NotesPeriode>{
    
    
     public void checkPeriodeSaisieNote() throws Exception {
        NotesPeriode np = (NotesPeriode) getOne(NotesPeriode.class, "select n from NotesPeriode n");
        if (np == null) {
            throw new BusinessException("Periode de saisie des notes non specifiée");
        }
        Date datejour=new Date();
        if(datejour.after(np.getDate_debut()) && datejour.before(np.getDate_fin())){
            
        }else{
           throw new BusinessException("Periode de saisie des notes depassée");  
        }
    }
}
