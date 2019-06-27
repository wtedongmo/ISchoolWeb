/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.model;


import com.tsoft.annotations.form.Select;
import com.tsoft.appli.highschool.model.Matiere;
import com.tsoft.appli.highschool.timetable.model.Cours;
import com.tsoft.security.model.Person;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class Professeur   extends Person{
    @OneToMany(mappedBy = "professeur")
    private List<Cours> cours;
    
    @Formula("(concat(code,concat('P',date_format(date_creation,'%Y'))))")
    private String matricule;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date_recrutement;
    @Column
    private  String diplome;
    @Column
    private boolean  vacataire;
    @JoinColumn(name = "code_matiere", referencedColumnName = "code")
    @ManyToOne
    @Select
    private Matiere   matiere;
    @Column
    private Byte  quota_horaire_hebdomadaire;
   

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    
   

    public Date getDate_recrutement() {
        return date_recrutement;
    }

    public void setDate_recrutement(Date date_recrutement) {
        this.date_recrutement = date_recrutement;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public boolean isVacataire() {
        return vacataire;
    }

    public void setVacataire(boolean vacataire) {
        this.vacataire = vacataire;
    }

    public Byte getQuota_horaire_hebdomadaire() {
        return quota_horaire_hebdomadaire;
    }

    public void setQuota_horaire_hebdomadaire(Byte quota_horaire_hebdomadaire) {
        this.quota_horaire_hebdomadaire = quota_horaire_hebdomadaire;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }

    
    
    
   
    
   
   
   
     
     
}
