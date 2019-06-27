/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model;

import com.tsoft.annotations.form.Label;
import com.tsoft.annotations.form.Phone;
import com.tsoft.security.model.superclass.LifeCycleEntity;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

@MappedSuperclass
public class PersonSuperClass extends LifeCycleEntity {
    @Size(
        max = 100
    )
    @Column(
        name = "email_adresse",
        length = 100
    )
    @Label("Email")
    private String emailAdresse;
    @Size(
        max = 100
    )
    @Column(
        name = "telephone_mobile",
        length = 100
    )
    @Label("Téléphone")
    @Phone
    private String telephoneMobile;
    @Column
    private String adresse;

    public PersonSuperClass() {
    }

    public String getEmailAdresse() {
        return this.emailAdresse;
    }

    public void setEmailAdresse(String emailAdresse) {
        this.emailAdresse = emailAdresse;
    }

    public String getTelephoneMobile() {
        return this.telephoneMobile;
    }

    public void setTelephoneMobile(String telephoneMobile) {
        this.telephoneMobile = telephoneMobile;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}

