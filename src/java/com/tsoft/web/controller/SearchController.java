/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.controller;

import com.tsoft.annotations.form.DoubleSelect;
import com.tsoft.dao.hibernate.service.HibernateServiceWrapper;
import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.utils.ObjectUtils;
import com.tsoft.utils.StringUtils;
import com.tsoft.utils.web.FormUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping({"/app"})
public class SearchController {
    protected static final Logger logger = Logger.getLogger(SearchController.class.getName());
    @Autowired
    HibernateEntityProperties emm;
    @Autowired
    HibernateServiceWrapper service;
    @Autowired
    FormUtils formutils;
    @Autowired
    ObjectUtils objectUtils;

    public SearchController() {
    }

    @RequestMapping(
        value = {"/{categorie}/search"},
        method = {RequestMethod.POST}
    )
    public void search(@PathVariable("categorie") String categorie, @RequestParam(value = "listCriteres",required = false) String listCriteres, HttpSession session, RedirectAttributes redirectattributes) throws Exception {
        String sql = "SELECT c FROM  " + categorie + "  c  ";
        if (listCriteres != null && !listCriteres.equals("")) {
            List<Field> joincolums = this.emm.getfieldsManytoOne(categorie);
            String sqlwhere = " where ";
            listCriteres = listCriteres.replaceFirst(";", "");
            String[] criterssplit = listCriteres.split(";");
            List<String> clauses = Arrays.asList(criterssplit);
            Iterator it = clauses.iterator();

            while(it.hasNext()) {
                String clause = (String)it.next();
                Field champjoin = null;
                String champ = clause.split(",")[0];
                Iterator var14 = joincolums.iterator();

                while(var14.hasNext()) {
                    Field f = (Field)var14.next();
                    if (f.getName().equalsIgnoreCase(champ)) {
                        champjoin = f;
                        break;
                    }
                }

                if (champjoin != null) {
                    Field libellechampjoin = this.emm.getfieldlibelle(champjoin.getType().getSimpleName());
                    if (libellechampjoin != null) {
                        champ = champjoin.getName() + "." + libellechampjoin.getName();
                    } else {
                        champ = champjoin.getName() + ".code";
                    }
                }

                String operand = clause.split(",")[1];
                String value = clause.split(",")[2];
                sqlwhere = sqlwhere + "   c." + champ + " " + operand + " " + StringUtils.wrap(value);
                if (it.hasNext()) {
                    sqlwhere = sqlwhere + "  and";
                }
            }

            sql = sql + sqlwhere;
        }

        logger.info("requete recherche " + sql);
        session.setAttribute("list" + categorie, this.service.getAll(this.emm.getCategorieClass(categorie), sql));
    }

    @RequestMapping(
        value = {"/{categorie}/search1"},
        method = {RequestMethod.POST}
    )
    public void simplesearch(@PathVariable String categorie, @RequestParam(value = "attribut",required = false) String attribut, @RequestParam(value = "searchvalue",required = false) String searchvalue, HttpSession session, RedirectAttributes redirectattributes) throws Exception {
        String sql = "SELECT c FROM  " + categorie + "  c  ";
        if (searchvalue != null && !searchvalue.equals("")) {
            List<Field> joincolums = this.emm.getfieldsManytoOne(categorie);
            String sqlwhere = " where ";
            Field champjoin = null;
            Iterator var10 = joincolums.iterator();

            while(var10.hasNext()) {
                Field f = (Field)var10.next();
                if (f.getName().equalsIgnoreCase(attribut)) {
                    champjoin = f;
                    break;
                }
            }

            if (champjoin != null) {
                Field libellechampjoin = this.emm.getfieldlibelle(champjoin.getType().getSimpleName());
                if (libellechampjoin != null) {
                    attribut = champjoin.getName() + "." + libellechampjoin.getName();
                } else {
                    attribut = champjoin.getName() + ".code";
                }
            }

            String operand = " LIKE ";
            sqlwhere = sqlwhere + "   c." + attribut + " " + operand + " " + StringUtils.wrap(searchvalue);
            sql = sql + sqlwhere;
        }

        logger.log(Level.INFO, "requete  simple recherche {0}", sql);
        session.setAttribute("list" + categorie, this.service.getAll(this.emm.getCategorieClass(categorie), sql));
    }

    @RequestMapping(
        value = {"/{categorie}/searchfull"},
        method = {RequestMethod.POST}
    )
    public void fullsearch(@PathVariable String categorie, @RequestParam(value = "fulltext",required = false) String searchvalue, HttpSession session, RedirectAttributes redirectattributes) throws Exception {
        session.setAttribute("list" + categorie, this.service.search(this.emm.getCategorieClass(categorie), this.emm.getfieldsearchable(categorie), searchvalue));
    }

    @RequestMapping(
        value = {"/{categorie}/autocomplete"},
        method = {RequestMethod.GET}
    )
    public JSONArray autocomplete(@PathVariable String categorie, @RequestParam String cval) throws Exception {
        return cval != null && cval.length() != 0 && cval.length() >= 4 ? this.formutils.getSelectModel(categorie, cval, (String)null) : new JSONArray();
    }

    @RequestMapping(
        value = {"/{categorie}/doubleselect/{implicantcol}/{implicantcolValue}"},
        method = {RequestMethod.GET}
    )
    public Map doubleselect(@PathVariable String categorie, @PathVariable String implicantcol, @PathVariable int implicantcolValue, HttpServletRequest req) throws Exception {
        Field champimplicant = ReflectionUtils.findField(this.emm.getCategorieClass(categorie), implicantcol);
        if (champimplicant.isAnnotationPresent(DoubleSelect.class)) {
            DoubleSelect ds = (DoubleSelect)champimplicant.getAnnotation(DoubleSelect.class);
            new ArrayList();
            List<Criterion> criteres = new ArrayList();
            List<Projection> projection = new ArrayList();
            criteres.add(Restrictions.eq(ds.searchBy(), implicantcolValue));
            if (ds.lookupImpliedCol().length() != 0) {
                projection.add(Property.forName(ds.lookupImpliedCol()).as("rs"));
            }

            List options = this.service.getAll(ds.subcategorie(), criteres, projection);
            Map result = new HashMap();
            result.put("name", ds.impliedcol());
            result.put("value", this.objectUtils.listToJson(options, req));
            return result;
        } else {
            return new HashMap();
        }
    }
}
