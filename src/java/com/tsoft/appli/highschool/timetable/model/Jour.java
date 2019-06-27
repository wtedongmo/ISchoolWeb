/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.timetable.model;

import com.tsoft.security.model.superclass.SimpleEntity;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author tchipi
 */
@Entity
public class Jour  extends SimpleEntity{
    
    @Column
    private String libelle;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    
    
}
