//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tsoft.metamodel;

import com.tsoft.dao.hibernate.service.HibernateServiceWrapper;
import com.tsoft.exceptions.NoSuchCategorieException;
import com.tsoft.service.process.ActionProcess;
import com.tsoft.utils.web.FieldUtils;
import com.tsoft.web.model.InputType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Formula;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.search.annotations.Indexed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;

@Component
@Scope("singleton")
public class HibernateEntityProperties implements EntityProperties, ApplicationContextAware {
    private ApplicationContext context;
    @Autowired
    HibernateServiceWrapper hsw;
    @Autowired
    SessionFactory sessionFactory;
    Map<String, Class<?>> mapclasscategories = new HashMap();

    public HibernateEntityProperties() {
    }

    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    public Map<String, Class<?>> getMapclasscategories() {
        return this.mapclasscategories;
    }

    @PostConstruct
    public void init() {
        SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl)this.sessionFactory;
        sessionFactoryImpl.getEntityPersisters().entrySet().stream().forEach((ent) -> {
            int pos = ((String)ent.getKey()).lastIndexOf(46);
            String categorie = ((String)ent.getKey()).substring(pos + 1);
            this.mapclasscategories.put(categorie, ((EntityPersister)ent.getValue()).getConcreteProxyClass());
        });
    }

    public boolean isSearchable(String categorie) throws NoSuchCategorieException {
        Class classcategorie = this.getCategorieClass(categorie);
        return classcategorie.isAnnotationPresent(Indexed.class);
    }

    public boolean isMultipart(String categorie) throws NoSuchCategorieException {
        Iterator var2 = this.getfields(categorie).iterator();

        Field f;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            f = (Field)var2.next();
        } while(!FieldUtils.getInputType(f).equals(InputType.FILE));

        return true;
    }

    public List<Field> getfields(String categorie) throws NoSuchCategorieException {
        ArrayList result = new ArrayList();

        try {
            Class classcategorie = this.getCategorieClass(categorie);
            ReflectionUtils.doWithFields(classcategorie, (field) -> {
                if (field.isAnnotationPresent(Id.class)) {
                    result.add(0, field);
                } else {
                    result.add(field);
                }

            }, (f) -> {
                return f.isAnnotationPresent(Formula.class) || f.isAnnotationPresent(Column.class) || f.isAnnotationPresent(JoinColumn.class);
            });
            return result;
        } catch (IllegalArgumentException | NoSuchCategorieException var4) {
            categorie = categorie.toLowerCase().substring(0, 1).concat(categorie.substring(1));
            if (this.context.containsBean(categorie)) {
                ReflectionUtils.doWithFields(((ActionProcess)this.context.getBean(categorie, ActionProcess.class)).getClass(), (f) -> {
                    result.add(f);
                }, (field) -> {
                    return !field.isAnnotationPresent(Autowired.class);
                });
                return result;
            } else {
                throw var4;
            }
        }
    }

    public String[] getfieldsName(String categorie) throws NoSuchCategorieException {
        List<String> fields = new ArrayList();
        String[] result = new String[0];
        Class classcategorie = this.getCategorieClass(categorie);
        ReflectionUtils.doWithFields(classcategorie, (f) -> {
            fields.add(f.getName());
        }, (f) -> {
            return f.isAnnotationPresent(Formula.class) || f.isAnnotationPresent(Column.class) || f.isAnnotationPresent(JoinColumn.class);
        });
        return (String[])fields.toArray(result);
    }

    public Object getinstance(String categorie) throws Exception {
        Class classcategorie = this.getCategorieClass(categorie);
        return this.hsw.addDefaultsValues(classcategorie.newInstance());
    }

    public Class getCategorieClass(String categorie) throws NoSuchCategorieException {
        if (categorie.indexOf("_$$_") != -1) {
            categorie = categorie.substring(0, categorie.indexOf("_$$_"));
        }

        Class c = (Class)this.mapclasscategories.get(categorie);
        if (c != null) {
            return c;
        } else {
            throw new NoSuchCategorieException("La rubrique " + categorie + " est  inexistante");
        }
    }

    public Field getfieldlibelle(String categorie) throws NoSuchCategorieException {
        List<Field> result = new ArrayList();
        Class classcategorie = this.getCategorieClass(categorie);
        ReflectionUtils.doWithFields(classcategorie, (f) -> {
            result.add(f);
        }, (field) -> {
            return FieldUtils.isfieldlibelle(field);
        });
        return result.isEmpty() ? null : (Field)result.get(0);
    }

    public Field getfieldCode(String categorie) throws NoSuchCategorieException {
        List<Field> result = new ArrayList();
        Class classcategorie = this.getCategorieClass(categorie);
        ReflectionUtils.doWithFields(classcategorie, (f) -> {
            result.add(f);
        }, (field) -> {
            return field.isAnnotationPresent(Id.class);
        });
        return result.isEmpty() ? null : (Field)result.get(0);
    }

    public String[] getfieldsearchable(String categorie) throws NoSuchCategorieException {
        return this.getfieldnameByAnnotation(categorie, org.hibernate.search.annotations.Field.class);
    }

    public String[] getfieldnameByAnnotation(String categorie, Class<? extends Annotation> a) throws NoSuchCategorieException {
        List<String> fields = new ArrayList();
        String[] result = new String[0];
        Class classcategorie = this.getCategorieClass(categorie);
        ReflectionUtils.doWithFields(classcategorie, (f) -> {
            fields.add(f.getName());
        }, (field) -> {
            return field.isAnnotationPresent(a);
        });
        return (String[])fields.toArray(result);
    }

    public List<Field> getfieldsManytoOne(String categorie) throws NoSuchCategorieException {
        return this.getfieldsByAnnotation(categorie, JoinColumn.class);
    }

    public List<Field> getfieldsOnetoMany(String categorie) throws NoSuchCategorieException {
        return this.getfieldsByAnnotation(categorie, OneToMany.class);
    }

    public List<Field> getfieldsByAnnotation(String categorie, Class<? extends Annotation> a) throws NoSuchCategorieException {
        List<Field> result = new ArrayList();
        Class classcategorie = this.getCategorieClass(categorie);
        ReflectionUtils.doWithFields(classcategorie, (f) -> {
            result.add(f);
        }, (field) -> {
            return field.isAnnotationPresent(a);
        });
        return result;
    }
}
