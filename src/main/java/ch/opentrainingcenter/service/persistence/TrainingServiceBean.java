package ch.opentrainingcenter.service.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.TrainingService;

@Stateless
public class TrainingServiceBean extends RepositoryServiceBean<Training> implements TrainingService {

    @PersistenceContext(unitName = "otc")
    private EntityManager entityManager;

    @Override
    public List<Training> findTrainingByAthlete(final Athlete athlete) {
        final TypedQuery<Training> query = entityManager.createNamedQuery("Training.getTrainingByAthlete", Training.class);
        query.setParameter("athlete", athlete);
        return query.getResultList();
    }

    @Override
    public Training findFullTraining(final int id) {
        final Training training = entityManager.find(Training.class, id);
        training.getTrackPoints().size();
        training.getLapInfos().size();
        return training;
    }

}
