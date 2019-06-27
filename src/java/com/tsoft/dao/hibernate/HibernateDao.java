//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tsoft.dao.hibernate;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;
import org.apache.lucene.search.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.FilterDef;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Transactional
@Repository
@Scope
public class HibernateDao<T> implements HibernateDaoInterface<T> {
    public static final Logger logger = Logger.getLogger("HibernateDAO");
    @Autowired
    SessionFactory sessionFactory;

    public HibernateDao() {
    }

    public T getById(Object id, Class<T> type) throws Exception {
        return (T) this.getCurrentSession().get(type, (Serializable)id);
    }

    public T load(Object id, Class<T> type) throws Exception {
        return (T) this.getCurrentSession().load(type, (Serializable)id);
    }

    public List<T> getAll(Class<T> type) throws Exception {
        return this.getAll(type, "select c from " + type.getSimpleName() + "   c");
    }

    public List getAll(Class<T> type, List<Criterion> criterias, List<Projection> projections) throws Exception {
        Criteria crit = this.getCurrentSession().createCriteria(type);
        ProjectionList pl = Projections.projectionList();
        if (!CollectionUtils.isEmpty(projections)) {
            projections.stream().forEach((p) -> {
                pl.add(p);
            });
            if (pl.getLength() != 0) {
                crit.setProjection(pl);
            }
        }

        criterias.stream().forEach((c) -> {
            crit.add(c);
        });
        System.out.println("\n"+crit.toString());
        return crit.list();
    }

    public List getAll(Class<T> type, String query) throws Exception {
        Session s = this.getCurrentSession();
        if (type != null && type.isAnnotationPresent(FilterDef.class)) {
            FilterDef f = (FilterDef)type.getAnnotation(FilterDef.class);
            s.enableFilter(f.name());
        }

        List result = s.createQuery(query).list();
        return result;
    }

    public T create(T entity) throws Exception {
        this.getCurrentSession().persist(entity);
        return entity;
    }

    public T update(T entity) throws Exception {
        this.getCurrentSession().merge(entity);
        return entity;
    }

    public T refresh(T entity) {
        this.getCurrentSession().refresh(entity);
        return entity;
    }

    public void delete(T entity) throws Exception {
        this.getCurrentSession().delete(entity);
    }

    public void deleteById(Object entityId, Class<T> type) throws Exception {
        T entity = this.getById(entityId, type);
        this.delete(entity);
    }

    protected final Session getCurrentSession() {
        Session s = this.sessionFactory.getCurrentSession();
        return s;
    }

    public List<T> search(Class<T> type, String[] fieldnamesearch, String searchword) {
        FullTextSession fullTextSession = Search.getFullTextSession(this.getCurrentSession());
        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(type).get();
        Query query = qb.keyword().onFields(fieldnamesearch).matching(searchword).createQuery();
        org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, new Class[]{type});
        List result = hibQuery.list();
        return result;
    }

    public void saveOrUpdate(T entity) throws Exception {
        this.getCurrentSession().saveOrUpdate(entity);
    }

    public void saveAll(List tosave) throws Exception {
        tosave.stream().forEach((o) -> {
            this.getCurrentSession().saveOrUpdate(o);
        });
    }

    public void deleteAll(List todel) throws Exception {
        Iterator var2 = todel.iterator();

        while(var2.hasNext()) {
            Object o = var2.next();
            this.delete((T) o);
        }

    }

    public T getOne(Class<T> type, List<Criterion> criterias, List<Projection> projections) throws Exception {
        List<T> list = this.getAll(type, criterias, projections);
        return !CollectionUtils.isEmpty(list) ? list.get(0) : null;
    }
}
