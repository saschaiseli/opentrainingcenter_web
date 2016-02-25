package ch.opentrainingcenter.data.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.TrainingService;

@SessionScoped
public class TrainingLoadingDelegator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TrainingService service;

    @Inject
    private Logger logger;

    private final List<Training> trainings = new ArrayList<>();

    @PostConstruct
    public void init() {
        logger.info("Load initially");
        trainings.addAll(service.getTraining(new Athlete()));
        logger.info("Anzahl Trainings: " + getTrainings().size());
    }

    public List<Training> getTrainings() {
        return trainings;
    }

}
