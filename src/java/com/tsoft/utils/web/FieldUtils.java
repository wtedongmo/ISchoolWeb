/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.utils.web;


import com.tsoft.annotations.form.DoubleSelect;
import com.tsoft.annotations.form.Fichier;
import com.tsoft.annotations.form.Label;
import com.tsoft.annotations.form.Libelle;
import com.tsoft.annotations.form.NotReadOnly;
import com.tsoft.annotations.form.OnChange;
import com.tsoft.annotations.form.Phone;
import com.tsoft.annotations.form.ReadOnly;
import com.tsoft.annotations.form.Secret;
import com.tsoft.annotations.form.Select;
import com.tsoft.annotations.form.SpelValue;
import com.tsoft.utils.StringUtils;
import com.tsoft.web.model.InputType;
import com.tsoft.web.model.SelectModel;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;
import org.json.simple.JSONArray;
import org.springframework.util.ReflectionUtils;

public class FieldUtils {
    public FieldUtils() {
    }

    public static JSONArray getSelectsEnumerated(String fieldname, Class categorieclass) {
        Field f = ReflectionUtils.findField(categorieclass, fieldname);
        return getSelectsEnumerated(f);
    }

    public static JSONArray getSelectsEnumerated(Field f) {
        Method m = ReflectionUtils.findMethod(f.getType(), "values");
        Object[] options = (Object[])((Object[])ReflectionUtils.invokeMethod(m, (Object)null));
        JSONArray ja = new JSONArray();
        Object[] var4 = options;
        int var5 = options.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Object option = var4[var6];
            SelectModel sm = new SelectModel();
            sm.setCode(option);
            sm.setLibelle(option);
            ja.add(sm);
        }

        return ja;
    }

    public static boolean isRequiredField(Field f) {
        return (f.isAnnotationPresent(NotNull.class) || f.isAnnotationPresent(Column.class) && !((Column)f.getAnnotation(Column.class)).nullable() || f.isAnnotationPresent(JoinColumn.class) && !((JoinColumn)f.getAnnotation(JoinColumn.class)).nullable() || f.isAnnotationPresent(Basic.class) && !((Basic)f.getAnnotation(Basic.class)).optional() || isfieldlibelle(f)) && !isReadOnlyField(f);
    }

    public static Integer getMaxLength(Field f) {
        if (f.isAnnotationPresent(Size.class)) {
            return ((Size)f.getAnnotation(Size.class)).max();
        } else {
            return f.isAnnotationPresent(Column.class) ? ((Column)f.getAnnotation(Column.class)).length() : 255;
        }
    }

    public static Integer getMinLenth(Field f) {
        return f.isAnnotationPresent(Size.class) ? ((Size)f.getAnnotation(Size.class)).min() : null;
    }

    public static Number getMax(Field f) {
        if (f.isAnnotationPresent(Max.class)) {
            return ((Max)f.getAnnotation(Max.class)).value();
        } else if (!f.getType().equals(Byte.class) && !f.getType().getName().equals("byte")) {
            return !f.getType().equals(Short.class) && !f.getType().getName().equals("short") ? null : 32767;
        } else {
            return 127;
        }
    }

    public static Number getMin(Field f) {
        if (f.isAnnotationPresent(Min.class)) {
            return ((Min)f.getAnnotation(Min.class)).value();
        } else if (!f.getType().equals(Byte.class) && !f.getType().getName().equals("byte")) {
            return !f.getType().equals(Short.class) && !f.getType().getName().equals("short") ? null : -32768;
        } else {
            return -128;
        }
    }

    public static boolean isReadOnlyField(Field f) {
        return (f.isAnnotationPresent(ReadOnly.class) || f.isAnnotationPresent(Id.class) && f.isAnnotationPresent(GeneratedValue.class) || f.isAnnotationPresent(Formula.class) || f.isAnnotationPresent(SpelValue.class)) && !f.isAnnotationPresent(NotReadOnly.class);
    }

    public static boolean isOnchange(Field f) {
        return f.isAnnotationPresent(OnChange.class);
    }

    public static boolean isfieldlibelle(Field f) {
        return f.isAnnotationPresent(Libelle.class) || f.getName().startsWith("libelle") || f.getName().equalsIgnoreCase("designation") || f.getName().equalsIgnoreCase("nom") || f.getName().equalsIgnoreCase("reference");
    }

    public static String getlabelField(Field f) {
        return f.isAnnotationPresent(Label.class) ? StringUtils.FirstLetterCapital(((Label)f.getAnnotation(Label.class)).value()) : StringUtils.coollabel(f.getName());
    }

    public static InputType getInputType(Field f) {
        if (!f.isAnnotationPresent(ManyToOne.class) && !f.isAnnotationPresent(JoinColumn.class)) {
            if (!f.getName().startsWith("password") && !f.isAnnotationPresent(Secret.class)) {
                if (f.isAnnotationPresent(URL.class)) {
                    return InputType.URL;
                } else if (f.isAnnotationPresent(Lob.class) && f.getType().equals(String.class)) {
                    return InputType.TEXTAREA;
                } else if (f.isAnnotationPresent(Fichier.class) && f.getType().equals(String.class)) {
                    return InputType.FILE;
                } else if (f.getType().equals(Date.class)) {
                    if (f.isAnnotationPresent(Temporal.class)) {
                        if (((Temporal)f.getAnnotation(Temporal.class)).value().equals(TemporalType.TIMESTAMP)) {
                            return InputType.TIMESTAMP;
                        }

                        if (((Temporal)f.getAnnotation(Temporal.class)).value().equals(TemporalType.TIME)) {
                            return InputType.TIME;
                        }
                    }

                    return InputType.DATE;
                } else if (!f.getName().startsWith("telephone") && !f.isAnnotationPresent(Phone.class)) {
                    if (!f.getName().startsWith("email") && !f.isAnnotationPresent(Email.class)) {
                        if (!f.getType().equals(Double.class) && !f.getType().equals(Float.class) && !f.getType().getName().equals("double") && !f.getType().getName().equals("float") && !f.getType().equals(Integer.class) && !f.getType().getName().equals("int") && !f.getType().equals(Byte.class) && !f.getType().getName().equals("byte") && !f.getType().equals(Short.class) && !f.getType().getName().equals("short") && !f.getType().equals(BigDecimal.class)) {
                            if (!f.getType().equals(Boolean.class) && !f.getType().getName().equals("boolean")) {
                                return f.isAnnotationPresent(Enumerated.class) ? InputType.SELECTENUM : InputType.TEXT;
                            } else {
                                return InputType.CHECKBOX;
                            }
                        } else {
                            return InputType.NUMBER;
                        }
                    } else {
                        return InputType.EMAIL;
                    }
                } else {
                    return InputType.TEL;
                }
            } else {
                return InputType.PASSWORD;
            }
        } else {
            return !f.getName().startsWith("type") && !f.isAnnotationPresent(Select.class) && !f.isAnnotationPresent(DoubleSelect.class) ? InputType.AUTOCOMPLETE : InputType.SELECT;
        }
    }

    public static String getPattern(Field f) {
        return null;
    }

    static String getPlaceHolder(Field f) {
        if (getInputType(f).equals(InputType.DATE)) {
            return "yyyy-mm-dd";
        } else if (getInputType(f).equals(InputType.TIME)) {
            return "HH:mm:ss";
        } else if (getInputType(f).equals(InputType.TIMESTAMP)) {
            return "yyyy-mm-ddTHH:mm:ss";
        } else if (getInputType(f).equals(InputType.TEL)) {
            return "237 6********";
        } else if (getInputType(f).equals(InputType.AUTOCOMPLETE)) {
            return "Autocomplete for you...";
        } else {
            return getInputType(f).equals(InputType.SELECT) ? "Select or search " + f.getType().getSimpleName() + " in the list..." : "";
        }
    }
}
