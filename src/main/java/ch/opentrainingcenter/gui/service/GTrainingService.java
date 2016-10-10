package ch.opentrainingcenter.gui.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.service.TrainingService;

@Stateless
public class GTrainingService {
    @Inject
    private TrainingService service;

    public GTraining loadTraining(final long dbId) {
        return new GTraining(service.findFullTraining(dbId));
    }
}
