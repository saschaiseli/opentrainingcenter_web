package ch.opentrainingcenter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.joda.time.DateTime;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.model.HeartRate;
import ch.opentrainingcenter.model.Route;
import ch.opentrainingcenter.model.RunData;
import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.model.Weather;

@Stateless
public class TrainingService {

    @Inject
    private Logger logger;

    public List<Training> getTraining(final Athlete athlete) {
        logger.info("getTraining from Service");
        final ArrayList<Training> trainings = new ArrayList<>();
        final DateTime start = DateTime.now().minusDays(2);
        final RunData runData = new RunData(start.getMillis(), 10, 100, 3);
        final HeartRate heart = new HeartRate(1, 2);
        final Weather weather = new Weather();
        final Route route = null;
        trainings.add(new Training(runData, heart, "note", weather, route));
        return trainings;
    }

}
