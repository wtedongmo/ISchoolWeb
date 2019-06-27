/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model;

import com.tsoft.annotations.form.ReadOnly;
import com.tsoft.annotations.form.Select;
import com.tsoft.security.model.superclass.LifeCycleEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Droit extends LifeCycleEntity {
    private static final long serialVersionUID = 1L;
    @NotNull
    @JoinColumn(
        name = "code_profil",
        referencedColumnName = "code",
        nullable = false
    )
    @ManyToOne
    @Select
    private Profil codeProfil;
    @NotNull
    @JoinColumn(
        name = "code_service",
        referencedColumnName = "code",
        nullable = false
    )
    @ManyToOne
    @Select
    private Services codeService;
    @NotNull
    @JoinColumn(
        name = "code_rubrique",
        referencedColumnName = "code",
        nullable = false
    )
    @ManyToOne
    @Select
    private Rubrique codeRubrique;
    @Lob
    @Column(
        length = 65535
    )
    @ReadOnly
    private String parametres;

    public String getParametres() {
        return this.parametres;
    }

    public void setParametres(String parametres) {
        this.parametres = parametres;
    }

    public Droit() {
    }

    public Droit(Integer code) {
        this.code = code;
    }

    public Profil getCodeProfil() {
        return this.codeProfil;
    }

    public void setCodeProfil(Profil codeProfil) {
        this.codeProfil = codeProfil;
    }

    public Services getCodeService() {
        return this.codeService;
    }

    public void setCodeService(Services codeService) {
        this.codeService = codeService;
    }

    public Rubrique getCodeRubrique() {
        return this.codeRubrique;
    }

    public void setCodeRubrique(Rubrique codeRubrique) {
        this.codeRubrique = codeRubrique;
    }
}
