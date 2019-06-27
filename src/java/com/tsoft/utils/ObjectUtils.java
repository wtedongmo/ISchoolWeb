/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.utils;

import com.tsoft.exceptions.NoSuchCategorieException;
import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.utils.web.FieldUtils;
import com.tsoft.web.model.InputType;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.number.NumberFormatter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

@Component
public class ObjectUtils {
    @Autowired
    HibernateEntityProperties emm;

    public ObjectUtils() {
    }

    public Object getCode(Object o) throws NoSuchCategorieException {
        return o == null ? null : this.getFieldValue(this.emm.getfieldCode(o.getClass().getSimpleName()), o);
    }

    public Object getFieldValue(Field f, Object o) {
        return this.getFieldValue(f.getName(), o);
    }

    public Object getFieldValue(String fieldname, Object o) {
        if (o == null) {
            return null;
        } else {
            BeanWrapper beanWrapper = new BeanWrapperImpl(o);
            return beanWrapper.getPropertyValue(fieldname);
        }
    }

    public void setFieldValue(Field f, Object o, Object value) {
        this.setFieldValue(f.getName(), o, value);
    }

    public void setFieldValue(String fname, Object o, Object value) {
        if (o != null) {
            BeanWrapper beanWrapper = new BeanWrapperImpl(o);
            beanWrapper.setPropertyValue(fname, value);
        }
    }

    public JSONObject objectToJson(Object o, HttpServletRequest req, boolean formmode) throws Exception {
        Locale locale = RequestContextUtils.getLocale(req);
        if (o == null) {
            return null;
        } else {
            JSONObject jo = new JSONObject();
            String categorie = o.getClass().getSimpleName();
            Iterator var7 = this.emm.getfields(categorie).iterator();

            while(true) {
                while(true) {
                    while(var7.hasNext()) {
                        Field f = (Field)var7.next();
                        Object toaffich = this.getFieldValue(f, o);
                        if (!f.isAnnotationPresent(JoinColumn.class) && !f.isAnnotationPresent(ManyToOne.class)) {
                            DateFormatter df;
                            if (!formmode) {
                                if (f.getType().equals(BigDecimal.class)) {
                                    NumberFormatter nf = new NumberFormatter();
                                    nf.setPattern("#,##0.00");
                                    jo.put(f.getName(), toaffich != null ? nf.print((BigDecimal)toaffich, locale) : null);
                                } else {
                                    df = new DateFormatter();
                                    if (FieldUtils.getInputType(f).equals(InputType.TIMESTAMP)) {
                                        df.setPattern("dd/MM/yyyy HH:mm:ss");
                                        jo.put(f.getName(), toaffich != null ? df.print((Date)toaffich, locale) : null);
                                    } else if (FieldUtils.getInputType(f).equals(InputType.DATE)) {
                                        df.setPattern("dd/MM/yyyy");
                                        jo.put(f.getName(), toaffich != null ? df.print((Date)toaffich, locale) : null);
                                    } else if (FieldUtils.getInputType(f).equals(InputType.TIME)) {
                                        df.setPattern("HH:mm");
                                        jo.put(f.getName(), toaffich != null ? df.print((Date)toaffich, locale) : null);
                                    } else {
                                        jo.put(f.getName(), toaffich);
                                    }
                                }
                            } else {
                                df = new DateFormatter();
                                if (!FieldUtils.getInputType(f).equals(InputType.TIMESTAMP) && !FieldUtils.getInputType(f).equals(InputType.DATE) && !FieldUtils.getInputType(f).equals(InputType.TIME)) {
                                    jo.put(f.getName(), toaffich);
                                } else {
                                    df.setPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                    jo.put(f.getName(), toaffich != null ? df.print((Date)toaffich, locale) : null);
                                }
                            }
                        } else {
                            jo.put(f.getName(), this.NestedobjectToJson(toaffich));
                        }
                    }

                    return jo;
                }
            }
        }
    }

    public JSONObject NestedobjectToJson(Object o) throws Exception {
        if (o == null) {
            return null;
        } else {
            JSONObject jo = new JSONObject();
            String categorie = o.getClass().getSimpleName();
            Field code = this.emm.getfieldCode(categorie);
            Field libelle = this.emm.getfieldlibelle(categorie);
            if (code != null) {
                jo.put(code.getName(), this.getFieldValue(code, o));
            }

            if (libelle != null) {
                jo.put(libelle.getName(), this.getFieldValue(libelle, o));
            }

            return jo;
        }
    }

    public JSONArray listToJson(Collection list, HttpServletRequest req) throws Exception {
        JSONArray array = new JSONArray();
        if (list != null && !list.isEmpty()) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                Object objectlist = var4.next();
                JSONObject jo = this.objectToJson(objectlist, req, false);
                array.add(jo);
            }

            return array;
        } else {
            return array;
        }
    }

    public JSONArray SelectModel(Collection list) throws Exception {
        JSONArray array = new JSONArray();
        if (list != null && !list.isEmpty()) {
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Object objectlist = var3.next();
                JSONObject jo = this.NestedobjectToJson(objectlist);
                array.add(jo);
            }

            return array;
        } else {
            return array;
        }
    }
}
