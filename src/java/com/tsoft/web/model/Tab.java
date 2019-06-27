/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.model;

import java.util.ArrayList;
import java.util.List;

public class Tab {
    private List<InputModel> inputs = new ArrayList();
    private String nom;
    private int nbrepannels;

    public Tab() {
    }

    public List<InputModel> getInputs() {
        return this.inputs;
    }

    public void setInputs(List<InputModel> inputs) {
        this.inputs = inputs;
    }

    public void addInput(InputModel im) {
        this.inputs.add(im);
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbrepannels() {
        return this.nbrepannels;
    }

    public void setNbrepannels(int nbrepannels) {
        this.nbrepannels = nbrepannels;
    }
}
