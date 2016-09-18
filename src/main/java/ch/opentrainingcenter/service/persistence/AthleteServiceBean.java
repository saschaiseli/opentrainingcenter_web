package ch.opentrainingcenter.service.persistence;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.service.AthleteService;

@Stateless
public class AthleteServiceBean extends RepositoryServiceBean<Athlete> implements AthleteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AthleteServiceBean.class);

	@Override
	public Athlete findByEmail(final String email) {

		final TypedQuery<Athlete> query = em.createNamedQuery("Athlete.findByEmail", Athlete.class);
		query.setParameter("email", email);
		Athlete result = null;
		try {
			result = query.getSingleResult();
			LOGGER.info("athlete with email '{}' found", email);
		} catch (final NoResultException noResult) {
			LOGGER.info("Athlete with email '{}' not found ", email);
		}
		return result;
	}

}
