package ch.opentrainingcenter.service.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.model.HeartRate;
import ch.opentrainingcenter.model.Route;
import ch.opentrainingcenter.model.RunData;
import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.model.Weather;
import ch.opentrainingcenter.service.TrainingService;

@Stateless
public class TrainingServiceBean implements TrainingService {

    @PersistenceContext(unitName = "otc")
    private EntityManager entityManager;

    public List<Training> getTraining(final Athlete athlete) {
        final ArrayList<Training> trainings = new ArrayList<>();
        final DateTime start = DateTime.now().minusDays(2);
        final RunData runData = new RunData(start.getMillis(), 10, 100, 3);
        final HeartRate heart = new HeartRate(1, 2);
        final Weather weather = new Weather();
        final Route route = null;
        trainings.add(new Training(runData, heart, "note", weather, route));
        return trainings;
    }

    @Override
    public int doSave(final Training training) {
        entityManager.persist(training);
        return training.getId();
    }

    @Override
    public Training findFullTraining(final int id) {
        final Training training = entityManager.find(Training.class, id);
        training.getTrackPoints().size();
        training.getLapInfos().size();
        return training;
    }

}
