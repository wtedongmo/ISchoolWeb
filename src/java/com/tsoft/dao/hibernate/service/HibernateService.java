//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tsoft.dao.hibernate.service;

import com.tsoft.dao.hibernate.HibernateDaoInterface;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class HibernateService<T> implements HibernateServiceInterface<T> {
    @Autowired
    protected HibernateDaoInterface dao;

    public HibernateService() {
    }

    public T getById(Object id, Class<T> type) throws Exception {
        return (T) this.dao.getById(id, type);
    }

    public T load(Object id, Class<T> type) throws Exception {
        return (T) this.dao.getById(id, type);
    }

    public final List<T> getAll(Class<T> type) throws Exception {
        return this.getAll(type, "select c from " + type.getSimpleName() + "   c");
    }

    public List getAll(Class<T> type, String query) throws Exception {
        return this.dao.getAll(type, query);
    }

    public List getAll(Class<T> type, List<Criterion> criterias, List<Projection> projections) throws Exception {
        return this.dao.getAll(type, criterias, projections);
    }

    public T getOne(Class<T> type, List<Criterion> criterias, List<Projection> projections) throws Exception {
        return (T) this.dao.getOne(type, criterias, projections);
    }

    public T getOne(Class<T> type, String query) throws Exception {
        List<T> li = this.dao.getAll(type, query);
        return li != null && !li.isEmpty() ? li.get(0) : null;
    }

    public T create(T entity) throws Exception {
        return (T) this.dao.create(entity);
    }

    public T addDefaultsValues(T entity) throws Exception {
        return entity;
    }

    public T update(T entity) throws Exception {
        return (T) this.dao.update(entity);
    }

    public T refresh(T entity) {
        return (T) this.dao.refresh(entity);
    }

    public void delete(T entity) throws Exception {
        this.dao.delete(entity);
    }

    public void deleteById(Object entityId, Class<T> type) throws Exception {
        T entity = this.getById(entityId, type);
        this.delete(entity);
    }

    public List<T> search(Class<T> type, String[] fieldnamesearch, String searchword) throws Exception {
        return this.dao.search(type, fieldnamesearch, searchword);
    }

    public void saveOrUpdate(T entity) throws Exception {
        this.dao.saveOrUpdate(entity);
    }

    public void saveAll(List tosave) throws Exception {
        this.dao.saveAll(tosave);
    }

    public void deleteAll(List tosave) throws Exception {
        this.dao.deleteAll(tosave);
    }
}
