package ch.opentrainingcenter.gui.controller;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.business.service.GTrainingService;
import ch.opentrainingcenter.gui.controller.Events.Select;
import ch.opentrainingcenter.gui.model.GTraining;

@Stateless
public class TrainingSelectionObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingSelectionObserver.class);

    @Inject
    private GTrainingService service;

    public void onSelection(@Observes @Select final GTraining selected) {
        LOGGER.info("Selected  Training {}", selected.getStartInMillis());
        final GTraining training = service.loadTraining(selected.getStartInMillis());
        onSelectSingleTraining(training);
    }

    void onSelectSingleTraining(final GTraining training) {
        LOGGER.info("blabla");
    };
}
