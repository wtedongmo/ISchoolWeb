/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.controller;

import com.tsoft.dao.hibernate.HibernateDao;
import com.tsoft.dao.hibernate.service.DefaultHibernateService;
import com.tsoft.security.model.Rubrique;
import com.tsoft.utils.ObjectUtils;
import com.tsoft.web.model.ListModel;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.OneToMany;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/app"})
public class RelationController extends MainController {
    @Autowired
    ObjectUtils objectUtils;
    @Autowired
    ModelController mc;
    @Autowired
    DefaultHibernateService service;

    public RelationController() {
    }

    @RequestMapping(
        value = {"/{categorie}/{row}/relations/{sfield}"},
        method = {RequestMethod.GET}
    )
    public ListModel relationlist(@PathVariable String categorie, @PathVariable String sfield, @PathVariable int row, HttpSession session, HttpServletRequest req) throws Exception {
        Object o = this.getSessionCategorieElement(session, categorie, row);
        Field f = ReflectionUtils.findField(o.getClass(), sfield);
        OneToMany otm = (OneToMany)f.getAnnotation(OneToMany.class);
        String mappedby = otm.mappedBy();
        HibernateDao.logger.log(Level.INFO, "sauvegarde de la valeur du mappedby  {0}", mappedby);
        Type t = f.getGenericType();
        ParameterizedType pt = (ParameterizedType)t;
        Type categorietype = pt.getActualTypeArguments()[0];
        String subcategorie = ((Class)categorietype).getSimpleName();
        HibernateDao.logger.log(Level.INFO, "on determine la sous categorie {0}", subcategorie);
        List listtoprint = (List)this.objectUtils.getFieldValue(sfield, o);
        if (listtoprint == null) {
            listtoprint = new ArrayList();
        }

        session.setAttribute("list" + subcategorie, listtoprint);
        Rubrique r = (Rubrique)this.service.getOne(Rubrique.class, "select r from Rubrique r where r.reference='" + subcategorie + "'");
        if (r == null) {
            r = new Rubrique();
            r.setReference(subcategorie);
            r.setLibelle(subcategorie);
        }

        ListModel lm = this.mc.getlistmodel(r, session, req);
        lm.setMappedby(mappedby);
        return lm;
    }
}
