/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.service.process;

import com.tsoft.dao.hibernate.service.HibernateServiceWrapper;
import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.sql.SQLQueryService;
import com.tsoft.utils.web.FieldUtils;
import com.tsoft.utils.web.FormUtils;
import com.tsoft.web.model.FormModel;
import com.tsoft.web.model.InputModel;
import com.tsoft.web.model.InputType;
import com.tsoft.web.model.Tab;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;

public abstract class EmptyActionProcess implements ActionProcess, InitializingBean {
    @Autowired
    protected ResourceLoader resourceLoader;
    @Autowired
    protected HibernateServiceWrapper hibernateService;
    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected SQLQueryService sqc;
    @Autowired
    protected FormUtils formUtils;
    @Autowired
    protected HibernateEntityProperties hep;

    public EmptyActionProcess() {
    }

    public String description() {
        return "";
    }

    public void afterPropertiesSet() throws Exception {
    }

    public final String form() throws Exception {
        return "tpl/form_process.html";
    }

    public Object initData(HttpSession session, HttpServletRequest request) throws Exception {
        return null;
    }

    public FormModel formModel(HttpSession session, HttpServletRequest request) throws Exception {
        FormModel fm = new FormModel();
        List<Tab> tabset = new ArrayList();
        Tab tab = new Tab();
        tab.setNom("Données d'entrée");
        tabset.add(tab);
        ReflectionUtils.doWithFields(this.getClass(), (f) -> {
            InputModel im = this.formUtils.getInputModelFromField(f, false);
            if (FieldUtils.getInputType(f).equals(InputType.FILE)) {
                fm.setIsmultipart(true);
            }

            if (FieldUtils.getInputType(f).equals(InputType.SELECT)) {
                fm.getSelectmodels().put(f.getName(), this.formUtils.getSelects(f));
            }

            if (FieldUtils.getInputType(f).equals(InputType.SELECTENUM)) {
                fm.getSelectmodels().put(f.getName(), FieldUtils.getSelectsEnumerated(f));
            }

            tab.addInput(im);
            if (f.isAnnotationPresent(ManyToOne.class) || f.isAnnotationPresent(JoinColumn.class)) {
                fm.getJoincolumns().add(f.getName());
            }

        }, (field) -> {
            return !field.isAnnotationPresent(Autowired.class);
        });
        fm.setTabset(tabset);
        fm.setLibelle(this.libelle());
        return fm;
    }
}
