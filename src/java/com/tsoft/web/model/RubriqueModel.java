/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.model;

import org.json.simple.JSONArray;

public class RubriqueModel {
    private JSONArray services;
    private String libelle;
    private String categorie;

    public RubriqueModel() {
    }

    public JSONArray getServices() {
        return this.services;
    }

    public void setServices(JSONArray services) {
        this.services = services;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
