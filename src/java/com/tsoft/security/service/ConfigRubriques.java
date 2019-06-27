/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.service;


import com.tsoft.security.model.Rubrique;
import com.tsoft.service.process.EmptyCustomActionProcess;
import com.tsoft.web.controller.ModelController;
import com.tsoft.web.converter.RubriquePropertyEditor;
import com.tsoft.web.model.FormModel;
import com.tsoft.web.model.InputModel;
import com.tsoft.web.model.ListModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/app"})
public class ConfigRubriques extends EmptyCustomActionProcess {
    @Autowired
    ModelController mc;
    @Autowired
    RubriquePropertyEditor rpe;

    public ConfigRubriques() {
    }

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Rubrique.class, this.rpe);
    }

    public String form() throws Exception {
        return "tpl/config_rubriques.html";
    }

    public String libelle() {
        return "Configuration des Rubriques";
    }

    @RequestMapping(
        value = {"/{categorie}/formConfig"},
        method = {RequestMethod.POST}
    )
    public void postFormConfig(@PathVariable Rubrique categorie, @RequestBody FormModel fm) throws Exception {
        categorie.setForm_description(this.formUtils.TabToString(fm.getTabset()));
        this.hibernateService.update(categorie);
    }

    @RequestMapping(
        value = {"/{categorie}/grillConfig"},
        method = {RequestMethod.POST}
    )
    public void postGrillConfig(@PathVariable Rubrique categorie, @RequestBody ListModel lm) throws Exception {
        categorie.setGrille_description(this.formUtils.ColumnToString(lm.getColumns()));
        this.hibernateService.update(categorie);
    }

    @RequestMapping(
        value = {"/{categorie}/formConfig"},
        method = {RequestMethod.GET}
    )
    public JSONObject getFormConfig(@PathVariable Rubrique categorie, HttpSession session, HttpServletRequest req) throws Exception {
        JSONObject jo = new JSONObject();
        List<InputModel> lims = new ArrayList();
        Iterator var6 = this.hep.getfields(categorie.getReference()).iterator();

        while(var6.hasNext()) {
            Field f = (Field)var6.next();
            lims.add(this.formUtils.getInputModelFromField(f, false));
        }

        jo.put("fields", lims);
        jo.put("fm", this.mc.getformmodel(categorie, (String)null, session));
        jo.put("lm", this.mc.getlistmodel(categorie, session, req));
        return jo;
    }

    public Class rubrique() throws Exception {
        return Rubrique.class;
    }
}
