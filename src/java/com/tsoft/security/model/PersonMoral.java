/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.hibernate.validator.constraints.URL;

@MappedSuperclass
public class PersonMoral extends PersonSuperClass {
    @Column
    @URL
    private String siteweb;
    @Column
    private String boite_postale;

    public PersonMoral() {
    }

    public String getSiteweb() {
        return this.siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    public String getBoite_postale() {
        return this.boite_postale;
    }

    public void setBoite_postale(String boite_postale) {
        this.boite_postale = boite_postale;
    }
}
