/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.dao.hibernate.service;

import com.tsoft.annotations.form.SpelValue;
import com.tsoft.dao.Dao;
import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.utils.ObjectUtils;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

@Component
public class HibernateServiceWrapper<T> extends HibernateService<T> implements ApplicationContextAware {
    private ApplicationContext context;
    @Autowired
    HibernateEntityProperties hep;
    @Autowired
    ObjectUtils objectUtils;

    public HibernateServiceWrapper() {
    }

    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    public HibernateService getHibernateService(String categorie) throws Exception {
        if (this.hep.getCategorieClass(categorie).isAnnotationPresent(Dao.class)) {
            Logger.getLogger(HibernateEntityProperties.class.getName()).info("utilisation dun DaoService particulier");
            Dao dao = (Dao)this.hep.getCategorieClass(categorie).getAnnotation(Dao.class);
            return (HibernateService)this.context.getBean(dao.value());
        } else {
            Logger.getLogger(HibernateEntityProperties.class.getName()).info("utilisation dun DaoService generic");
            return (HibernateService)this.context.getBean(DefaultHibernateService.class);
        }
    }

    public HibernateService getHibernateService(Object entity) throws Exception {
        return this.getHibernateService(entity.getClass().getSimpleName());
    }

    public HibernateService getHibernateService(Class entityClass) throws Exception {
        return this.getHibernateService(entityClass.getSimpleName());
    }

    public T create(T entity) throws Exception {
        this.setSpelValues(entity);
        HibernateService hs = this.getHibernateService(entity);
        return (T) hs.create(entity);
    }

    public T addDefaultsValues(T entity) throws Exception {
        HibernateService hs = this.getHibernateService(entity);
        return (T) hs.addDefaultsValues(entity);
    }

    public void saveOrUpdate(T entity) throws Exception {
        this.setSpelValues(entity);
        HibernateService hs = this.getHibernateService(entity);
        hs.saveOrUpdate(entity);
    }

    public T update(T entity) throws Exception {
        this.setSpelValues(entity);
        HibernateService hs = this.getHibernateService(entity);
        return (T) hs.update(entity);
    }

    public void deleteById(Object entityId, Class<T> type) throws Exception {
        HibernateService hs = this.getHibernateService(type);
        hs.deleteById(entityId, type);
    }

    public void saveAll(List tosave) throws Exception {
        Iterator var2 = tosave.iterator();

        while(var2.hasNext()) {
            Object entity = var2.next();
            this.setSpelValues(entity);
            HibernateService hs = this.getHibernateService(entity);
            hs.saveOrUpdate(entity);
        }

    }

    public void setSpelValues(Object entity) throws Exception {
        ExpressionParser parser = new SpelExpressionParser();
        Iterator var3 = this.hep.getfieldsByAnnotation(entity.getClass().getSimpleName(), SpelValue.class).iterator();

        while(var3.hasNext()) {
            Field f = (Field)var3.next();

            try {
                Object value = parser.parseExpression(((SpelValue)f.getAnnotation(SpelValue.class)).value()).getValue(entity, f.getType());
                this.objectUtils.setFieldValue(f, entity, value);
            } catch (Exception var6) {
                ;
            }
        }

    }
}
