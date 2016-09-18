package ch.opentrainingcenter.service.persistence;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.model.Shoe;
import ch.opentrainingcenter.service.ShoeService;

@Stateless
public class ShoeServiceBean extends RepositoryServiceBean<Shoe> implements ShoeService {

	@Override
	public Collection<Shoe> getShoesByAthlete(final Athlete athlete) {
		final TypedQuery<Shoe> query = em.createNamedQuery("Shoe.getShoesByAthlete", Shoe.class);
		query.setParameter("athlete", athlete);
		return query.getResultList();
	}

	@Override
	public int getUsedMeters(final Shoe shoe) {
		final Query query = em.createNamedQuery("Shoe.getKilometers");
		query.setParameter("shoe", shoe);
		final Object value = query.getSingleResult();
		int result;
		if (value != null) {
			result = Integer.valueOf(value.toString());
		} else {
			result = 0;
		}
		return result;
	}

}
