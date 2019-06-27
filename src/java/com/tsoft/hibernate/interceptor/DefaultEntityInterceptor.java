//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tsoft.hibernate.interceptor;

import com.tsoft.security.model.User;
import com.tsoft.utils.enumerations.DataLifeCycle;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;

public class DefaultEntityInterceptor extends EmptyInterceptor {
    protected static final Logger logger = Logger.getLogger(DefaultEntityInterceptor.class.getName());

    public DefaultEntityInterceptor() {
    }

    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        for(int i = 0; i < propertyNames.length; ++i) {
            if ("cycle_vie".equals(propertyNames[i]) && state[i] != null && state[i].equals(DataLifeCycle.VALIDE)) {
                throw new HibernateException("impossible de supprimer les données deja validées");
            }
        }

    }

    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        boolean result = false;

        for(int i = 0; i < propertyNames.length; ++i) {
            if ("date_creation".equals(propertyNames[i])) {
                state[i] = new Date();
                result = true;
            }

            if ("code_createur".equals(propertyNames[i])) {
                try {
                    state[i] = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                } catch (Exception var9) {
                    ;
                }

                result = true;
            }

            if ("password".equals(propertyNames[i])) {
                try {
                    ShaPasswordEncoder spe = new ShaPasswordEncoder();
                    state[i] = spe.encodePassword(state[i].toString(), (Object)null);
                } catch (Exception var10) {
                    ;
                }

                result = true;
            }
        }

        return result;
    }

    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        boolean result = false;

        for(int i = 0; i < propertyNames.length; ++i) {
            if ("cycle_vie".equals(propertyNames[i])) {
                if (previousState != null && previousState[i] != null && previousState[i].equals(DataLifeCycle.VALIDE)) {
                    throw new HibernateException("impossible de mettre à jour les données deja validées");
                }

                result = true;
            }

            if ("date_modification".equals(propertyNames[i])) {
                currentState[i] = new Date();
                result = true;
            }

            if ("code_modificateur".equals(propertyNames[i])) {
                try {
                    currentState[i] = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                } catch (Exception var11) {
                    ;
                }

                result = true;
            }

            if ("password".equals(propertyNames[i])) {
                if (!currentState[i].equals(previousState[i])) {
                    ShaPasswordEncoder spe = new ShaPasswordEncoder();
                    String passwordencoded = spe.encodePassword(currentState[i].toString(), (Object)null);
                    currentState[i] = passwordencoded;
                }

                result = true;
            }
        }

        return result;
    }

    public void afterTransactionCompletion(Transaction tx) {
    }
}
