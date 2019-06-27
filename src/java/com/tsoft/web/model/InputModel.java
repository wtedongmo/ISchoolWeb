/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.model;


public class InputModel {
    private String label;
    private String name;
    private boolean required = false;
    private boolean readonly;
    private InputType type;
    private String categorie;
    private String flibelle;
    private Number max;
    private Number min;
    private Integer maxlength;
    private Integer minlength;
    private String placeholder;
    private String pattern;
    private boolean onchange;

    public InputModel() {
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public Integer getMaxlength() {
        return this.maxlength;
    }

    public void setMaxlength(Integer maxlength) {
        this.maxlength = maxlength;
    }

    public Integer getMinlength() {
        return this.minlength;
    }

    public void setMinlength(Integer minlength) {
        this.minlength = minlength;
    }

    public Number getMax() {
        return this.max;
    }

    public void setMax(Number max) {
        this.max = max;
    }

    public Number getMin() {
        return this.min;
    }

    public void setMin(Number min) {
        this.min = min;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isReadonly() {
        return this.readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public InputType getType() {
        return this.type;
    }

    public void setType(InputType type) {
        this.type = type;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getFlibelle() {
        return this.flibelle;
    }

    public void setFlibelle(String flibelle) {
        this.flibelle = flibelle;
    }

    public boolean isOnchange() {
        return this.onchange;
    }

    public void setOnchange(boolean onchange) {
        this.onchange = onchange;
    }
}
