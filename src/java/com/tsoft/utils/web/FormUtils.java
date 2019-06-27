/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.utils.web;

import com.tsoft.dao.hibernate.service.DefaultHibernateService;
import com.tsoft.exceptions.NoSuchCategorieException;
import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.security.model.Rubrique;
import com.tsoft.utils.ObjectUtils;
import com.tsoft.utils.web.datatables.DatatableColumnModel;
import com.tsoft.web.model.InputModel;
import com.tsoft.web.model.Tab;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FormUtils {
    @Autowired
    protected HibernateEntityProperties emm;
    @Autowired
    ObjectUtils objectutils;
    @Autowired
    private DefaultHibernateService dhs;

    public FormUtils() {
    }

    public InputModel getInputModelFromField(String fiedlname, String categorie) {
        try {
            Field f = ReflectionUtils.findField(this.emm.getCategorieClass(categorie), fiedlname);
            return this.getInputModelFromField(f, false);
        } catch (NoSuchCategorieException var4) {
            return null;
        }
    }

    public InputModel getInputModelFromField(Field f, boolean putreadonly) {
        InputModel im = new InputModel();
        im.setLabel(FieldUtils.getlabelField(f));
        im.setName(f.getName());
        im.setType(FieldUtils.getInputType(f));
        im.setReadonly(FieldUtils.isReadOnlyField(f) || putreadonly);
        im.setPlaceholder(im.isReadonly() ? "" : FieldUtils.getPlaceHolder(f));
        im.setRequired(FieldUtils.isRequiredField(f));
        im.setMax(FieldUtils.getMax(f));
        im.setMin(FieldUtils.getMin(f));
        im.setMaxlength(FieldUtils.getMaxLength(f));
        im.setMinlength(FieldUtils.getMinLenth(f));
        im.setPattern(FieldUtils.getPattern(f));
        im.setOnchange(FieldUtils.isOnchange(f));
        if (f.isAnnotationPresent(ManyToOne.class) || f.isAnnotationPresent(JoinColumn.class)) {
            String subcategorie = f.getType().getSimpleName();
            if (f.isAnnotationPresent(ManyToOne.class) && !((ManyToOne)f.getAnnotation(ManyToOne.class)).targetEntity().equals(Void.TYPE)) {
                subcategorie = ((ManyToOne)f.getAnnotation(ManyToOne.class)).targetEntity().getSimpleName();
            }

            Field ff = null;

            try {
                ff = this.emm.getfieldlibelle(subcategorie);
            } catch (NoSuchCategorieException var7) {
                Logger.getLogger(FormUtils.class.getName()).log(Level.SEVERE, (String)null, var7);
            }

            im.setCategorie(subcategorie);
            im.setFlibelle(ff != null ? ff.getName() : null);
        }

        return im;
    }

    public String TabToString(List<Tab> tabs) {
        String result = "";
        if (tabs != null && !tabs.isEmpty()) {
            Tab tab;
            String inputs;
            for(Iterator var3 = tabs.iterator(); var3.hasNext(); result = result + tab.getNom() + "=" + inputs + ";" + tab.getNbrepannels() + "\n") {
                tab = (Tab)var3.next();
                inputs = "";
                inputs = (String)tab.getInputs().stream().map((im) -> {
                    return im.getName() + ",";
                }).reduce(inputs, String::concat);
                inputs = inputs.substring(0, inputs.length() - 1);
            }

            return result;
        } else {
            return null;
        }
    }

    public String ColumnToString(List<DatatableColumnModel> columns) {
        String result = "";
        if (columns != null && !columns.isEmpty()) {
            DatatableColumnModel c;
            for(Iterator var3 = columns.iterator(); var3.hasNext(); result = result + c.getName() + ",") {
                c = (DatatableColumnModel)var3.next();
            }

            result = result.substring(0, result.length() - 1);
            return result;
        } else {
            return null;
        }
    }

    public List<Tab> StringToTab(String form_description, String categorie) {
        List<Tab> listtabs = new ArrayList();
        String[] tabs = form_description.split("\n");
        String[] var5 = tabs;
        int var6 = tabs.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String tab = var5[var7];
            String tabvalue = tab.split("=")[1];
            String tabname = tab.split("=")[0];
            String inputs = tabvalue.split(";")[0];
            Tab t = new Tab();
            t.setNom(tabname);
            t.setNbrepannels(Integer.parseInt(tabvalue.split(";")[1]));
            String[] var13 = inputs.split(",");
            int var14 = var13.length;

            for(int var15 = 0; var15 < var14; ++var15) {
                String input = var13[var15];
                InputModel im = this.getInputModelFromField(input, categorie);
                t.addInput(im);
            }

            listtabs.add(t);
        }

        return listtabs;
    }

    public List<Tab> designForm(Rubrique categorie) throws Exception {
        return (List)(StringUtils.hasText(categorie.getForm_description()) ? this.StringToTab(categorie.getForm_description(), categorie.getReference()) : new ArrayList());
    }

    public JSONArray getSelects(String fieldname, Class categorieclass) {
        Field f = ReflectionUtils.findField(categorieclass, fieldname);
        return this.getSelects(f);
    }

    public JSONArray getSelects(Field f) {
        try {
            return this.getSelectModel(f.getType().getSimpleName(), (String)null, (String)null);
        } catch (Exception var3) {
            Logger.getLogger(FieldUtils.class.getName()).log(Level.SEVERE, (String)null, var3);
            return new JSONArray();
        }
    }

    public JSONArray getSelectModel(String categorie, String value, String searchby) throws NoSuchCategorieException, Exception {
        Field flibelle = this.emm.getfieldlibelle(categorie);
        if (flibelle == null) {
            return new JSONArray();
        } else {
            String flibelles = flibelle.getName();
            if (flibelle.isAnnotationPresent(JoinColumn.class)) {
                flibelles = flibelles + "." + this.emm.getfieldlibelle(flibelle.getType().getSimpleName()).getName();
            }

            new ArrayList();
            List options = this.dhs.getAll(this.emm.getCategorieClass(categorie), "select c  from " + categorie + " c  " + (value != null ? "where  c." + (searchby != null ? searchby : flibelles) + "  like '%" + value + "%'" : ""));
            return this.objectutils.SelectModel(options);
        }
    }
}
