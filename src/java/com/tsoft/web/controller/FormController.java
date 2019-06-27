/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.controller;


import com.tsoft.dao.hibernate.service.HibernateServiceWrapper;
import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.utils.ObjectUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.OneToMany;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping({"/app"})
public class FormController extends MainController {
    @Autowired
    HibernateEntityProperties emm;
    @Autowired
    ObjectUtils objectUtils;
    @Autowired
    HibernateServiceWrapper service;
    @Autowired
    ConversionServiceFactoryBean csfb;
    protected static final Logger logger = Logger.getLogger(FormController.class.getName());

    public FormController() {
    }

    @RequestMapping(
        value = {"/{categorie}/del/{code}"},
        method = {RequestMethod.GET}
    )
    public void del(@PathVariable int code, @PathVariable String categorie, HttpSession session, @RequestParam Integer row, HttpServletRequest request) throws Exception {
        List listresult = this.getSessionCategorieData(session, categorie);
        this.service.deleteById(code, this.emm.getCategorieClass(categorie));
        listresult.remove(row);
        session.setAttribute("list" + categorie, listresult);
    }

    @RequestMapping(
        value = {"/{categorie}/getAll"},
        method = {RequestMethod.GET}
    )
    public JSONArray getAll(@PathVariable String categorie, HttpServletRequest req) throws Exception {
        List listobjets = this.service.getAll(this.emm.getCategorieClass(categorie));
        return this.objectUtils.listToJson(listobjets, req);
    }

    @RequestMapping(
        value = {"/{categorie}/saveMultiPart"},
        method = {RequestMethod.POST}
    )
    public JSONObject saveMultipart(HttpSession session, @PathVariable String categorie, @RequestParam(required = false,defaultValue = "-1") Integer row, MultipartHttpServletRequest request) throws Exception {
        return this.save(session, categorie, row, request);
    }

    @RequestMapping(
        value = {"/{categorie}/save"},
        method = {RequestMethod.POST}
    )
    public JSONObject save(HttpSession session, @PathVariable String categorie, @RequestParam(required = false,defaultValue = "-1") Integer row, HttpServletRequest request) throws Exception {
        List listresult = this.getSessionCategorieData(session, categorie);
        Object o = null;
        logger.log(Level.INFO, row == -1 ? "operation d''insertion sur la rubrique {0}" : "operation de mise a jour sur la rubrique {0}", categorie);
        if (row == -1) {
            o = this.emm.getinstance(categorie);
        } else {
            o = listresult.get(row);
        }

        ServletRequestDataBinder binder = new ServletRequestDataBinder(o);
        binder.setConversionService(this.csfb.getObject());
        binder.bind(request);
        binder.validate();
        BindingResult results = binder.getBindingResult();
        String error = "";
        if (results.hasFieldErrors()) {
            error = (String)results.getFieldErrors().stream().map((fe) -> {
                return fe.getField() + ":" + fe.getRejectedValue() + " " + fe.getDefaultMessage();
            }).reduce(error, String::concat);
            throw new Exception(error);
        } else {
            if (row == -1) {
                this.service.create(o);
                o = this.service.load(this.objectUtils.getFieldValue(this.emm.getfieldCode(categorie), o), this.emm.getCategorieClass(categorie));
                listresult.add(o);
            } else {
                this.service.update(o);
                o = this.service.load(this.objectUtils.getFieldValue(this.emm.getfieldCode(categorie), o), this.emm.getCategorieClass(categorie));
                listresult.set(row, o);
            }

            session.setAttribute("list" + categorie, listresult);
            return this.getElement(session, categorie, row == -1 ? listresult.size() - 1 : row, request);
        }
    }

    @RequestMapping(
        value = {"/{categorie}/onChange"},
        method = {RequestMethod.POST}
    )
    public JSONObject onChange(HttpSession session, @PathVariable String categorie, @RequestParam(required = false,defaultValue = "-1") Integer row, HttpServletRequest request) throws Exception {
        Object o = null;
        o = this.emm.getinstance(categorie);
        ServletRequestDataBinder binder = new ServletRequestDataBinder(o);
        binder.setConversionService(this.csfb.getObject());
        binder.bind(request);
        this.service.setSpelValues(o);
        new JSONObject();
        JSONObject jo = this.objectUtils.objectToJson(o, request, true);
        return jo;
    }

    @RequestMapping(
        value = {"/{categorie}/getElementByCode/{code}"},
        method = {RequestMethod.GET}
    )
    public JSONObject getElementByCode(HttpSession session, @PathVariable String categorie, @PathVariable Integer code, HttpServletRequest request) throws Exception {
        Object o = this.service.getById(code, this.emm.getCategorieClass(categorie));
        List listresult = new ArrayList();
        if (o != null) {
            listresult.add(o);
        }

        session.setAttribute("list" + categorie, listresult);
        return this.getElement(session, categorie, o == null ? -1 : 0, request);
    }

    @RequestMapping(
        value = {"/{categorie}/getElement/{row}"},
        method = {RequestMethod.GET}
    )
    public JSONObject getElement(HttpSession session, @PathVariable String categorie, @PathVariable int row, HttpServletRequest request) throws Exception {
        Object o = this.emm.getinstance(categorie);
        List listresult = this.getSessionCategorieData(session, categorie);
        new JSONObject();
        if (row != -1) {
            o = listresult.get(row);
            o = this.service.load(this.objectUtils.getFieldValue(this.emm.getfieldCode(categorie), o), this.emm.getCategorieClass(categorie));
        }

        JSONObject jo = this.objectUtils.objectToJson(o, request, true);
        jo.put("row", row);
        jo.put("categorie", categorie);
        jo.put("nbrow", listresult.size());
        return jo;
    }

    @RequestMapping(
        value = {"/{categorie}/getElementClone/{row}"},
        method = {RequestMethod.GET}
    )
    public JSONObject getElementClone(HttpSession session, @PathVariable String categorie, @PathVariable int row, HttpServletRequest request) throws Exception {
        Object o = this.emm.getinstance(categorie);
        List listresult = this.getSessionCategorieData(session, categorie);
        new JSONObject();
        if (row != -1) {
            o = listresult.get(row);
        }

        Object target = this.emm.getinstance(categorie);
        BeanUtils.copyProperties(o, target, this.emm.getfieldnameByAnnotation(categorie, OneToMany.class));
        this.objectUtils.setFieldValue(this.emm.getfieldCode(categorie), target, (Object)null);
        JSONObject jo = this.objectUtils.objectToJson(target, request, true);
        jo.put("row", -1);
        jo.put("categorie", categorie);
        jo.put("nbrow", listresult.size());
        return jo;
    }

    @RequestMapping(
        value = {"/{supcategorie}/{suprow}/{categorie}/getElement1/{row}/{sfield}"},
        method = {RequestMethod.GET}
    )
    public JSONObject getElementmodal(HttpSession session, @PathVariable String categorie, @PathVariable String supcategorie, @PathVariable String sfield, @PathVariable int row, @PathVariable int suprow, HttpServletRequest request) throws Exception {
        Object o = this.emm.getinstance(categorie);
        List listresult = this.getSessionCategorieData(session, categorie);
        new JSONObject();
        if (row != -1) {
            o = listresult.get(row);
        } else {
            Object supo = this.getSessionCategorieElement(session, supcategorie, suprow);
            this.objectUtils.setFieldValue(sfield, o, supo);
        }

        JSONObject jo = this.objectUtils.objectToJson(o, request, true);
        jo.put("row", row);
        jo.put("categorie", categorie);
        jo.put("nbrow", listresult.size());
        return jo;
    }

    @RequestMapping(
        value = {"/{categorie}/onchange/{field}"},
        method = {RequestMethod.POST}
    )
    public JSONObject onPropertyChange(HttpSession session, @PathVariable String categorie, @PathVariable String field, HttpServletRequest request) throws Exception {
        Object o = this.emm.getinstance(categorie);
        ServletRequestDataBinder binder = new ServletRequestDataBinder(o);
        binder.setConversionService(this.csfb.getObject());
        binder.bind(request);
        return this.objectUtils.objectToJson(o, request, true);
    }
}
