/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model;

import com.tsoft.annotations.form.Label;
import com.tsoft.annotations.form.Select;
import com.tsoft.security.model.superclass.SimpleEntity;
import com.tsoft.utils.enumerations.TypeService;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Services extends SimpleEntity {
    @Size(
        max = 100
    )
    @Column(
        length = 100,
        unique = true
    )
    private String libelle;
    @Column(
        length = 100
    )
    private String reference;
    @Column(
        length = 65535
    )
    @Lob
    private String description;
    @Column
    @Enumerated(EnumType.STRING)
    @Label("Type Service")
    private TypeService type;
    @NotNull
    @JoinColumn(
        name = "code_rubrique",
        referencedColumnName = "code",
        nullable = false
    )
    @ManyToOne
    @Select
    private Rubrique rubrique;

    public Services() {
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeService getType() {
        return this.type;
    }

    public void setType(TypeService type) {
        this.type = type;
    }

    public Rubrique getRubrique() {
        return this.rubrique;
    }

    public void setRubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
    }
}
