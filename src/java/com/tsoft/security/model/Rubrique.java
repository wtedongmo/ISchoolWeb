/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model;

import com.tsoft.annotations.form.ReadOnly;
import com.tsoft.annotations.form.Select;
import com.tsoft.security.model.superclass.SimpleEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table
public class Rubrique extends SimpleEntity {
    @Size(
        max = 100
    )
    @Column(
        length = 100,
        unique = true
    )
    private String libelle;
    @Column(
        length = 100,
        unique = true
    )
    private String reference;
    @Lob
    @Column(
        length = 65535
    )
    @ReadOnly
    private String form_description;
    @Lob
    @Column(
        length = 65535
    )
    @ReadOnly
    private String grille_description;
    @JoinColumn(
        name = "code_menu",
        referencedColumnName = "code"
    )
    @ManyToOne
    @Select
    @NotNull
    private Menu codeMenu;
    @JoinColumn(
        name = "code_icon",
        referencedColumnName = "code"
    )
    @ManyToOne
    @Select
    private Icon icon;
    @OneToMany(
        mappedBy = "rubrique"
    )
    private List<Services> services = new ArrayList();

    public Rubrique() {
    }

    public Rubrique(int i) {
        this.code = i;
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

    public String getForm_description() {
        return this.form_description;
    }

    public void setForm_description(String form_description) {
        this.form_description = form_description;
    }

    public String getGrille_description() {
        return this.grille_description;
    }

    public void setGrille_description(String grille_description) {
        this.grille_description = grille_description;
    }

    public Menu getCodeMenu() {
        return this.codeMenu;
    }

    public void setCodeMenu(Menu codeMenu) {
        this.codeMenu = codeMenu;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public List<Services> getServices() {
        return this.services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }
}
