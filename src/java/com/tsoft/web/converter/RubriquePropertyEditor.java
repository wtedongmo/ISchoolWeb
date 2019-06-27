/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.converter;


import com.tsoft.dao.hibernate.service.DefaultHibernateService;
import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.security.model.Rubrique;
import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RubriquePropertyEditor extends PropertyEditorSupport {
    @Autowired
    DefaultHibernateService service;
    @Autowired
    HibernateEntityProperties epi;

    public RubriquePropertyEditor() {
    }

    public void setAsText(String text) throws IllegalArgumentException {
        try {
            this.epi.getCategorieClass(text);
            Rubrique r = (Rubrique)this.service.getOne(Rubrique.class, "select r from Rubrique r where r.reference='" + text + "'");
            if (r == null) {
                r = new Rubrique();
                r.setReference(text);
                r.setLibelle(text);
            }

            this.setValue(r);
        } catch (Exception var3) {
            throw new IllegalArgumentException("Rubrique " + text + " inexistante");
        }
    }
}
