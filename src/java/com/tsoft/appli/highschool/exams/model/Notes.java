/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.exams.model;

import com.tsoft.annotations.form.Select;
import com.tsoft.appli.highschool.exams.service.NotesService;
import com.tsoft.appli.highschool.model.EleveInscrit;
import com.tsoft.dao.Dao;
import com.tsoft.security.model.superclass.LifeCycleEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
@Dao(NotesService.class)
public class Notes extends LifeCycleEntity {

    @NotNull
    @JoinColumn(name = "code_eleveinscrit", referencedColumnName = "code")
    @ManyToOne
    private EleveInscrit eleveinscrit;
    @JoinColumn(name = "code_coefficient", referencedColumnName = "code")
    @ManyToOne
    @Select
    @NotNull
    private Coefficient coefficient;

    @Column
    @NotNull
    @Min(0)
    @Max(20)
    private double note;

    @JoinColumn(name = "code_sequence", referencedColumnName = "code")
    @ManyToOne
    @Select
    @NotNull
    private Sequence numero_sequence;

    public Notes() {
    }

    public Notes(int codenote) {
        this.code = codenote;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public Sequence getNumero_sequence() {
        return numero_sequence;
    }

    public void setNumero_sequence(Sequence numero_sequence) {
        this.numero_sequence = numero_sequence;
    }

    public EleveInscrit getEleveinscrit() {
        return eleveinscrit;
    }

    public void setEleveinscrit(EleveInscrit eleveinscrit) {
        this.eleveinscrit = eleveinscrit;
    }

    public Coefficient getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Coefficient coefficient) {
        this.coefficient = coefficient;
    }

   
    

}
