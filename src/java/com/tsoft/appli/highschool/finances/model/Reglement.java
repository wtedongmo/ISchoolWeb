/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.finances.model;

import com.tsoft.annotations.form.Fichier;
import com.tsoft.annotations.form.Libelle;
import com.tsoft.appli.highschool.finances.service.ReglementService;
import com.tsoft.appli.highschool.model.Eleve;
import com.tsoft.appli.highschool.model.EleveInscrit;
import com.tsoft.dao.Dao;
import com.tsoft.security.model.superclass.LifeCycleEntity;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.servlet.annotation.MultipartConfig;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
@Dao(ReglementService.class)
public class Reglement extends LifeCycleEntity {

    @Column
    @NotNull
    private BigDecimal montant;

    @NotNull
    @JoinColumn(name = "code_eleveinscrit", referencedColumnName = "code")
    @ManyToOne
    private EleveInscrit eleveinscrit;
    @Enumerated(EnumType.STRING)
    @Column
    private ObjectMvtCaisse objet;
    @Column
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_reglement;
    
    @Column
    @Fichier
    private String file_reference;
    //@Libelle
//    @Column
//    private String reference;
    
    @JoinColumn(name = "code_eleve", referencedColumnName = "code")
    @ManyToOne
    private Eleve eleve;
    
    public EleveInscrit getEleveinscrit() {
        return eleveinscrit;
    }

    public void setEleveinscrit(EleveInscrit eleveinscrit) {
        this.eleveinscrit = eleveinscrit;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public ObjectMvtCaisse getObjet() {
        return objet;
    }

    public void setObjet(ObjectMvtCaisse objet) {
        this.objet = objet;
    }

    public Date getDate_reglement() {
        return date_reglement;
    }

    public void setDate_reglement(Date date_reglement) {
        this.date_reglement = date_reglement;
    }
    
    public String getFile_reference() {
        return file_reference;
    }

    public void setFile_reference(String file_reference) {
        this.file_reference = file_reference;
    }
    
    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    
}
