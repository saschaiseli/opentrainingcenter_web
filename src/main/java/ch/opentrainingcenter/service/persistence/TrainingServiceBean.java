package ch.opentrainingcenter.service.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.TrainingService;

@Stateless
public class TrainingServiceBean extends RepositoryServiceBean<Training> implements TrainingService {

	@Override
	public List<Training> findTrainingByAthlete(final Athlete athlete) {
		final TypedQuery<Training> query = em.createNamedQuery("Training.getTrainingByAthlete", Training.class);
		query.setParameter("athlete", athlete);
		return query.getResultList();
	}

	@Override
	public Training findFullTraining(final int id) {
		final Training training = em.find(Training.class, id);
		// final CriteriaBuilder cb = em.getCriteriaBuilder();
		// final CriteriaQuery<Training> cq = cb.createQuery(Training.class);
		// final Root<Training> from = cq.from(Training.class);
		// from.fetch("trackPoints");

		training.getTrackPoints().size();
		training.getLapInfos().size();
		return training;
	}

}
