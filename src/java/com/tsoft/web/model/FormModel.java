/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.model;

import com.tsoft.utils.enumerations.TypeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;

public class FormModel extends RubriqueModel {
    private boolean ismultipart;
    private boolean access;
    private List<Tab> tabset;
    private Map<String, JSONArray> selectmodels = new HashMap();
    private List<String> joincolumns = new ArrayList();
    private List<SelectModel> relations = new ArrayList();
    private TypeService typeservice;

    public FormModel() {
    }

    public boolean isIsmultipart() {
        return this.ismultipart;
    }

    public void setIsmultipart(boolean ismultipart) {
        this.ismultipart = ismultipart;
    }

    public boolean isAccess() {
        return this.access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public List<Tab> getTabset() {
        return this.tabset;
    }

    public void setTabset(List<Tab> tabset) {
        this.tabset = tabset;
    }

    public List<SelectModel> getRelations() {
        return this.relations;
    }

    public void setRelations(List<SelectModel> relations) {
        this.relations = relations;
    }

    public void addRelations(SelectModel relation) {
        this.relations.add(relation);
    }

    public Map<String, JSONArray> getSelectmodels() {
        return this.selectmodels;
    }

    public void setSelectmodels(Map<String, JSONArray> selectmodels) {
        this.selectmodels = selectmodels;
    }

    public List<String> getJoincolumns() {
        return this.joincolumns;
    }

    public void setJoincolumns(List<String> joincolumns) {
        this.joincolumns = joincolumns;
    }

    public TypeService getTypeservice() {
        return this.typeservice;
    }

    public void setTypeservice(TypeService typeservice) {
        this.typeservice = typeservice;
    }
}
