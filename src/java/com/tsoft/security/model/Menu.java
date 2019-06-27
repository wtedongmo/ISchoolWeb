/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model;

import com.tsoft.annotations.form.Select;
import com.tsoft.security.model.superclass.SimpleEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Menu extends SimpleEntity {
    @Size(
        max = 100
    )
    @Column(
        length = 100,
        unique = true
    )
    @NotNull
    private String libelle;
    @JoinColumn(
        name = "code_icon",
        referencedColumnName = "code"
    )
    @ManyToOne
    @Select
    private Icon icon;

    public Menu() {
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
