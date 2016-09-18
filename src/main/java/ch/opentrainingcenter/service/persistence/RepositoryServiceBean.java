package ch.opentrainingcenter.service.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.opentrainingcenter.service.RepositoryService;

@Stateless
public class RepositoryServiceBean<T> implements RepositoryService<T> {

	@PersistenceContext(unitName = "otc")
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
	public void remove(final Class<T> type, final int id) {
		final T managed = em.find(type, id);
		em.remove(managed);
	}

	@Override
	public T find(final Class<T> type, final int id) {
		return em.find(type, id);
	}

}
