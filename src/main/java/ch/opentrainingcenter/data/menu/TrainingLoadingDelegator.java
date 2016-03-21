package ch.opentrainingcenter.data.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.controller.MyApplicationScope;
import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.TrainingService;
import ch.opentrainingcenter.util.Events.Added;

@SessionScoped
public class TrainingLoadingDelegator implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingLoadingDelegator.class);
    private static final long serialVersionUID = 1L;

    @Inject
    private TrainingService service;

    @Inject
    private MyApplicationScope scope;

    private final List<Training> trainings = new ArrayList<>();

    @PostConstruct
    public void init() {
        LOGGER.info("Load initially");
        trainings.addAll(service.findTrainingByAthlete(scope.getApplicationUser()));
        LOGGER.info("Anzahl Trainings: " + getTrainings().size());
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void onAddTraining(@Observes @Added final Training training) {
        LOGGER.info("onAddTraining: {}", training);
        trainings.add(training);
    }
}
