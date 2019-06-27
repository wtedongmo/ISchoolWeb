/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.timetable.model;

import com.tsoft.annotations.form.Label;
import com.tsoft.security.model.superclass.SimpleEntity;
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
public class Creneau extends SimpleEntity {
    
    @Formula("concat(date_format(heure_debut,'%H:%i'),concat('-',date_format(heure_fin,'%H:%i')))")
    private String libelle;

    @NotNull
    @Column
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date heure_debut;
    @NotNull
    @Column
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date heure_fin;
    
    @Column
    @Label("Pause")
    private boolean disponible;
    
   
    
    
//    @Formula
//    private double duree;

    public Date getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(Date heure_debut) {
        this.heure_debut = heure_debut;
    }

    public Date getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(Date heure_fin) {
        this.heure_fin = heure_fin;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    
    

}
