/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.exams.model;

import com.tsoft.security.model.superclass.SimpleEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
public class NotesPeriode  extends SimpleEntity{
    
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull
    private Date date_debut;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull
    private Date date_fin;

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }
    
    
    
}
