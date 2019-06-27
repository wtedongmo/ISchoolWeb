/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.finances.model;

import com.tsoft.security.model.User;
import com.tsoft.security.model.superclass.SimpleEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class Caisse extends SimpleEntity {

    @Column
    private String libelle;

    @Formula("(select mvt.solde from MouvementCaisse mvt where mvt.code_caisse=code order by mvt.date_mouvement desc limit 1)")
    private BigDecimal solde_theorique;
    @Column
    private BigDecimal solde_reel;
    @Formula("(select mvt.solde-solde_reel from MouvementCaisse mvt where mvt.code_caisse=code order by mvt.date_mouvement desc limit 1)")
    private BigDecimal ecart;

    @JoinColumn(name = "code_gerant", referencedColumnName = "code")
    @ManyToOne
    @NotNull
    private User gerant;
    @Enumerated(EnumType.ORDINAL)
    @Column
    private EtatCaisse etat_caisse;

//    @OneToMany(mappedBy = "caisse")
//    private List<EntreeCaisse> encaissements = new ArrayList();
//    @OneToMany(mappedBy = "caisse")
//    private List<SortieCaisse> decaissements = new ArrayList();

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public BigDecimal getSolde_theorique() {
        return solde_theorique;
    }

    public void setSolde_theorique(BigDecimal solde_theorique) {
        this.solde_theorique = solde_theorique;
    }

    public BigDecimal getSolde_reel() {
        return solde_reel;
    }

    public void setSolde_reel(BigDecimal solde_reel) {
        this.solde_reel = solde_reel;
    }

    public BigDecimal getEcart() {
        return ecart;
    }

    public void setEcart(BigDecimal ecart) {
        this.ecart = ecart;
    }

    public User getGerant() {
        return gerant;
    }

    public void setGerant(User gerant) {
        this.gerant = gerant;
    }

   
   

    
    public EtatCaisse getEtat_caisse() {
        return etat_caisse;
    }

    public void setEtat_caisse(EtatCaisse etat_caisse) {
        this.etat_caisse = etat_caisse;
    }

    
}
