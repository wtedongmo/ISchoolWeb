/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.finances.model;

import com.tsoft.annotations.form.Libelle;
import com.tsoft.annotations.form.ReadOnly;
import com.tsoft.annotations.form.Select;
import com.tsoft.annotations.form.SpelValue;
import com.tsoft.appli.highschool.finances.service.MvtCaisseService;
import com.tsoft.dao.Dao;
import com.tsoft.security.model.superclass.AuditEntity;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "type",
        discriminatorType = DiscriminatorType.STRING
)
@Dao(MvtCaisseService.class)
public class MouvementCaisse extends AuditEntity {

    @Formula("(concat(code,concat('/M/',date_format(date_creation,'%m/%Y'))))")
    @Libelle
    private String reference;
    @Column
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_mouvement;
    @Column
    @NotNull
    private BigDecimal montant;
    @Column
    @ReadOnly
    private BigDecimal solde;
    @Column
    @SpelValue("caisse.solde_theorique")
    private BigDecimal solde_initial;

    @JoinColumn(name = "code_caisse", referencedColumnName = "code")
    @ManyToOne
    @Select
    @NotNull
    protected Caisse caisse;

    @Column
    @Lob
    private String motif;

    @Enumerated(EnumType.STRING)
    @Column(name = "objet_mvt")
    private ObjectMvtCaisse objet_Mouvement;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getDate_mouvement() {
        return date_mouvement;
    }

    public void setDate_mouvement(Date date_mouvement) {
        this.date_mouvement = date_mouvement;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public ObjectMvtCaisse getObjet_Mouvement() {
        return objet_Mouvement;
    }

    public void setObjet_Mouvement(ObjectMvtCaisse objet_Mouvement) {
        this.objet_Mouvement = objet_Mouvement;
    }

  

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public BigDecimal getSolde_initial() {
        return solde_initial;
    }

    public void setSolde_initial(BigDecimal solde_initial) {
        this.solde_initial = solde_initial;
    }

}
