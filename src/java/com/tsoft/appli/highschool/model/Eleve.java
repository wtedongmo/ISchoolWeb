/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.annotations.form.Fichier;
import com.tsoft.annotations.form.Phone;
import com.tsoft.security.model.Person;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import org.hibernate.annotations.Formula;
import org.hibernate.search.annotations.Indexed;

/**
 *
 * @author tchipi
 */
@Entity
@Indexed
public class Eleve extends Person {

    //@Column
    @Formula("(concat(code,concat('E',date_format(date_creation,'%Y'))))")
    private String matricule;

    //adress Parents
    @Column
    private String nom_pere;
    @Column
    private String profession_pere;
    @Column
    @Phone
    private String telephone_pere;
    @Column
    private String nom_mere;
    @Column
    @Phone
    private String telephone_mere;
    @Column
    private String profession_mere;

    @Column
    @Fichier
    private String photo;

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom_pere() {
        return nom_pere;
    }

    public void setNom_pere(String nom_pere) {
        this.nom_pere = nom_pere;
    }

    public String getProfession_pere() {
        return profession_pere;
    }

    public void setProfession_pere(String profession_pere) {
        this.profession_pere = profession_pere;
    }

    public String getTelephone_pere() {
        return telephone_pere;
    }

    public void setTelephone_pere(String telephone_pere) {
        this.telephone_pere = telephone_pere;
    }

    public String getNom_mere() {
        return nom_mere;
    }

    public void setNom_mere(String nom_mere) {
        this.nom_mere = nom_mere;
    }

    public String getTelephone_mere() {
        return telephone_mere;
    }

    public void setTelephone_mere(String telephone_mere) {
        this.telephone_mere = telephone_mere;
    }

    public String getProfession_mere() {
        return profession_mere;
    }

    public void setProfession_mere(String profession_mere) {
        this.profession_mere = profession_mere;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

   

   
    
    

}
