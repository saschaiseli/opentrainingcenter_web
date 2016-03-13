package ch.opentrainingcenter.service.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.opentrainingcenter.model.Training;

@Stateless
public class TrainingServiceBean {// implements TrainingService {

    @PersistenceContext(unitName = "otc")
    private EntityManager entityManager;

    // public List<Training> getTraining(final Athlete athlete) {
    // logger.info("getTraining from Service");
    // final ArrayList<Training> trainings = new ArrayList<>();
    // final DateTime start = DateTime.now().minusDays(2);
    // final RunData runData = new RunData(start.getMillis(), 10, 100, 3);
    // final HeartRate heart = new HeartRate(1, 2);
    // final Weather weather = new Weather();
    // final Route route = null;
    // trainings.add(new Training(runData, heart, "note", weather, route));
    // return trainings;
    // }

    public int doSave(final Training training) {
        entityManager.persist(training);
        return training.getId();
    }

    public Training load(final int id) {
        return entityManager.find(Training.class, id);
    }

}
