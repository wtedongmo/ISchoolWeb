/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.test.service;


import com.tsoft.security.model.superclass.SimpleEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

public class TDATE extends SimpleEntity {
    @Column
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date_test;
    @Column
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date heure_test;
    @Column
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime_test;

    public TDATE() {
    }

    public Date getDate_test() {
        return this.date_test;
    }

    public void setDate_test(Date date_test) {
        this.date_test = date_test;
    }

    public Date getHeure_test() {
        return this.heure_test;
    }

    public void setHeure_test(Date heure_test) {
        this.heure_test = heure_test;
    }

    public Date getDatetime_test() {
        return this.datetime_test;
    }

    public void setDatetime_test(Date datetime_test) {
        this.datetime_test = datetime_test;
    }
}
