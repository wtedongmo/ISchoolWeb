/*
 * Noteso change this license header, choose License Headers in Project Properties.
 * Noteso change this template file, choose Notesools | Notesemplates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.exams.service;

import com.tsoft.appli.highschool.exams.model.Notes;
import com.tsoft.dao.hibernate.service.HibernateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class NotesService extends HibernateService<Notes> {

    @Autowired
    NotesPeriodeService nps;

    @Override
    public Notes create(final Notes entity) throws Exception {
        nps.checkPeriodeSaisieNote();
        return super.create(entity);
    }

    @Override
    public Notes update(final Notes entity) throws Exception {
        nps.checkPeriodeSaisieNote();
        return super.update(entity);
    }

    @Override
    public void saveOrUpdate(Notes entity) throws Exception {
        nps.checkPeriodeSaisieNote();
        super.saveOrUpdate(entity);
    }
}
