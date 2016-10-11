package ch.opentrainingcenter.business.dbaccess.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import ch.opentrainingcenter.business.dbaccess.RepositoryService;

@Stateless
public class RepositoryServiceBean<T> implements RepositoryService<T> {

    @PersistenceContext(unitName = "otc", type = PersistenceContextType.TRANSACTION)
    protected EntityManager em;

    @Override
    public T doSave(final T t) {
        em.persist(t);
        return t;
    }

    @Override
    public T update(final T t) {
        return em.merge(t);
    }

    @Override
    public void remove(final Class<T> type, final long id) {
        final T managed = em.find(type, id);
        em.remove(managed);
    }

    @Override
    public T find(final Class<T> type, final long id) {
        return em.find(type, id);
    }

}
