/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.timetable.model;

import com.tsoft.annotations.form.ReadOnly;
import com.tsoft.annotations.form.Select;
import com.tsoft.annotations.form.SpelValue;
import com.tsoft.appli.highschool.model.AnneeScolaire;
import com.tsoft.appli.highschool.model.Classe;
import com.tsoft.appli.highschool.model.Professeur;
import com.tsoft.appli.highschool.timetable.service.CoursService;
import com.tsoft.dao.Dao;
import com.tsoft.security.model.superclass.SimpleEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
@Dao(CoursService.class)
public class Cours extends SimpleEntity {

    @SpelValue("professeur.nom_prenom +'-'+ classe.libelle")
    @Column
    private String libelle;

    @Formula("(select m.libelle from Matiere m join Professeur p on m.code=p.code_matiere where p.code=code_professeur)")
    private String matiere;

    @NotNull
    @JoinColumn(name = "code_professeur", referencedColumnName = "code")
    @Select
    @ManyToOne
    private Professeur professeur;

    @NotNull
    @JoinColumn(name = "code_classe", referencedColumnName = "code")
    @Select
    @ManyToOne
    private Classe classe;

    @NotNull
    @JoinColumn(name = "code_annee", referencedColumnName = "code")
    @ManyToOne
    @ReadOnly
    @Select
    private AnneeScolaire annee;

    @Column
    @NotNull
    private Short duree_heures;

    @Formula("duree_heures*60")
    private Short duree_minutes;

    public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Short getDuree_heures() {
        return duree_heures;
    }

    public void setDuree_heures(Short duree_heures) {
        this.duree_heures = duree_heures;
    }

    public Short getDuree_minutes() {
        return duree_minutes;
    }

    public void setDuree_minutes(Short duree_minutes) {
        this.duree_minutes = duree_minutes;
    }

   

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public AnneeScolaire getAnnee() {
        return annee;
    }
    public void setAnnee(AnneeScolaire annee) {
        this.annee = annee;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

   
   

}
