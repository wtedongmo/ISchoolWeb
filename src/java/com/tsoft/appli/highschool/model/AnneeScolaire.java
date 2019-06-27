/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.security.model.superclass.LifeCycleEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class AnneeScolaire extends LifeCycleEntity {

    @Formula("concat(date_format(date_debut,'%Y'),concat('/',date_format(date_fin,'%Y')))")
    private String libelle;
    @Column
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date_debut;
    @Column
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date_fin;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

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
