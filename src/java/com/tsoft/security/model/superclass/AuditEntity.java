/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model.superclass;

import com.tsoft.annotations.form.Label;
import com.tsoft.annotations.form.ReadOnly;
import com.tsoft.annotations.form.Secure;
import com.tsoft.security.model.User;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AuditEntity extends SimpleEntity {
    @Column
    @Secure
    @ReadOnly
    @Label("Date Dernière Modification")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date date_modification;
    @JoinColumn(
        name = "code_modificateur",
        referencedColumnName = "code"
    )
    @ManyToOne
    @Secure
    @ReadOnly
    @Label("Dernier Modificateur")
    protected User code_modificateur;
    @Column
    @Secure
    @ReadOnly
    @Temporal(TemporalType.TIMESTAMP)
    protected Date date_creation;
    @JoinColumn(
        name = "code_createur",
        referencedColumnName = "code"
    )
    @ManyToOne
    @Secure
    @ReadOnly
    protected User code_createur;

    public AuditEntity() {
    }

    public Date getDate_modification() {
        return this.date_modification;
    }

    public void setDate_modification(Date date_modification) {
        this.date_modification = date_modification;
    }

    public User getCode_modificateur() {
        return this.code_modificateur;
    }

    public void setCode_modificateur(User code_modificateur) {
        this.code_modificateur = code_modificateur;
    }

    public Date getDate_creation() {
        return this.date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public User getCode_createur() {
        return this.code_createur;
    }

    public void setCode_createur(User code_createur) {
        this.code_createur = code_createur;
    }
}
