/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model;

import com.tsoft.annotations.form.Label;
import com.tsoft.annotations.form.Libelle;
import com.tsoft.utils.enumerations.Civilite;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.search.annotations.Field;

@MappedSuperclass
public class Person extends PersonSuperClass {
    @NotNull
    @Column
    @Libelle
    @Field
    @Label("Nom Et Prénom")
    private String nom_prenom;
    @Column
    @Enumerated(EnumType.STRING)
    private Civilite civilite;
    @Column(
        name = "date_naissance",
        length = 100
    )
    @Label("Né(e) le")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    @Size(
        max = 100
    )
    @Column(
        name = "lieu_naissance",
        length = 100
    )
    private String lieuNaissance;
    @Column
    private Integer numero_cni;
    @Column
    @Temporal(TemporalType.DATE)
    private Date date_delivrance_cni;
    @Column
    private String lieu_delivrance_cni;

    public Person() {
    }

    public Date getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return this.lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getNom_prenom() {
        return this.nom_prenom;
    }

    public void setNom_prenom(String nom_prenom) {
        this.nom_prenom = nom_prenom;
    }

    public Civilite getCivilite() {
        return this.civilite;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public Integer getNumero_cni() {
        return this.numero_cni;
    }

    public void setNumero_cni(Integer numero_cni) {
        this.numero_cni = numero_cni;
    }

    public Date getDate_delivrance_cni() {
        return this.date_delivrance_cni;
    }

    public void setDate_delivrance_cni(Date date_delivrance_cni) {
        this.date_delivrance_cni = date_delivrance_cni;
    }

    public String getLieu_delivrance_cni() {
        return this.lieu_delivrance_cni;
    }

    public void setLieu_delivrance_cni(String lieu_delivrance_cni) {
        this.lieu_delivrance_cni = lieu_delivrance_cni;
    }
}
