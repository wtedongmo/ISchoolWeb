/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.annotations.form.Select;
import com.tsoft.annotations.form.SpelValue;
import com.tsoft.appli.highschool.timetable.model.Cours;
import com.tsoft.security.model.superclass.SimpleEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SqlFragmentAlias;

/**
 *
 * @author tchipi
 */
@Entity
public class Classe extends SimpleEntity {
    @OneToMany(mappedBy = "classe")
    private List<Cours> cours;

    @Column
    @SpelValue("codeSerie.abreviation +(codeSerie.nbre_Classes + 1)")
    private String libelle;
    @JoinColumn(name = "code_serie", referencedColumnName = "code")
    @ManyToOne
    @Select
    @NotNull
    private Serie codeSerie;
    @Formula("(select count(ei.code) from Classe c left join EleveInscrit ei on c.code=ei.code_classe  where c.code=code"
            + "  and ei.cycle_vie='ACTIF')")
    private Integer nbre_Eleves;

    @OneToMany(mappedBy = "classe")
    @Filter(name = "isok", condition = "{t}.cycle_vie like 'ACTIF'", deduceAliasInjectionPoints = false,
            aliases = {
                @SqlFragmentAlias(alias = "t", table = "EleveInscrit")})
    private List<EleveInscrit> ListElevesInscrits;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Serie getCodeSerie() {
        return codeSerie;
    }

    public void setCodeSerie(Serie codeSerie) {
        this.codeSerie = codeSerie;
    }

    public List<EleveInscrit> getListElevesInscrits() {
        return ListElevesInscrits;
    }

    public void setListElevesInscrits(List<EleveInscrit> ListElevesInscrits) {
        this.ListElevesInscrits = ListElevesInscrits;
    }

    public Integer getNbre_Eleves() {
        return nbre_Eleves;
    }

    public void setNbre_Eleves(Integer nbre_Eleves) {
        this.nbre_Eleves = nbre_Eleves;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }

    
}
