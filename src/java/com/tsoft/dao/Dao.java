//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tsoft.dao;

import com.tsoft.dao.hibernate.service.DefaultHibernateService;
import com.tsoft.dao.hibernate.service.HibernateService;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Dao {
    Class<? extends HibernateService> value() default DefaultHibernateService.class;
}
