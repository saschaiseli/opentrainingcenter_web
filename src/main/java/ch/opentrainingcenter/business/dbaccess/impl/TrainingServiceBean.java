package ch.opentrainingcenter.business.dbaccess.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import ch.opentrainingcenter.business.dbaccess.TrainingService;
import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Training;

@Stateless
public class TrainingServiceBean extends RepositoryServiceBean<Training> implements TrainingService {

    @Override
    public List<Training> findTrainingByAthlete(final Athlete athlete) {
        final TypedQuery<Training> query = em.createNamedQuery("Training.getTrainingByAthlete", Training.class);
        query.setParameter("athlete", athlete);
        return query.getResultList();
    }

    @Override
    public Training findFullTraining(final long id) {
        final Training training = em.find(Training.class, id);
        training.getTrackPoints().size();
        training.getLapInfos().size();
        return training;
    }

}
