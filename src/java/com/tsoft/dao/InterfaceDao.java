//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tsoft.dao;

import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;

public interface InterfaceDao<T> {
    T getById(Object var1, Class<T> var2) throws Exception;

    T load(Object var1, Class<T> var2) throws Exception;

    List getAll(Class<T> var1, String var2) throws Exception;

    List<T> getAll(Class<T> var1) throws Exception;

    List getAll(Class<T> var1, List<Criterion> var2, List<Projection> var3) throws Exception;

    T getOne(Class<T> var1, List<Criterion> var2, List<Projection> var3) throws Exception;

    List<T> search(Class<T> var1, String[] var2, String var3) throws Exception;

    T create(T var1) throws Exception;

    void saveOrUpdate(T var1) throws Exception;

    T update(T var1) throws Exception;

    T refresh(T var1);

    void deleteById(Object var1, Class<T> var2) throws Exception;

    void delete(T var1) throws Exception;

    void deleteAll(List var1) throws Exception;

    void saveAll(List var1) throws Exception;
}
