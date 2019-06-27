/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model;

import com.tsoft.annotations.form.Label;
import com.tsoft.security.model.superclass.LifeCycleEntity;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Profil extends LifeCycleEntity {
    private static final long serialVersionUID = 1L;
    @Basic(
        optional = false
    )
    @NotNull
    @Size(
        min = 1,
        max = 100
    )
    @Column(
        nullable = false,
        length = 100
    )
    private String libelle;
    @OneToMany(
        mappedBy = "codeProfil"
    )
    @Label("Liste des Permissions")
    private List<Droit> droitList;
    @OneToMany(
        mappedBy = "codeProfil"
    )
    @Label("Liste des Utilisateurs")
    private List<UserProfil> userprofilList;

    public Profil(Integer code) {
        this.code = code;
    }

    public Profil() {
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @XmlTransient
    public List<Droit> getDroitList() {
        return this.droitList;
    }

    public void setDroitList(List<Droit> droitList) {
        this.droitList = droitList;
    }

    @XmlTransient
    public List<UserProfil> getUserprofilList() {
        return this.userprofilList;
    }

    public void setUserprofilList(List<UserProfil> userprofilList) {
        this.userprofilList = userprofilList;
    }

    public int hashCode() {
        int hash = 0;
        hash += (this.code != null ? this.code.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Profil)) {
            return false;
        } else {
            Profil other = (Profil)object;
            return (this.code != null || other.code == null) && (this.code == null || this.code.equals(other.code));
        }
    }

    public String toString() {
        return "com.tsoft.model.Profil[ code=" + this.code + " ]";
    }
}
