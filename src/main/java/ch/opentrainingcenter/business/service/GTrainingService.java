package ch.opentrainingcenter.business.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ch.opentrainingcenter.business.dbaccess.TrainingService;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.business.service.converter.ConvertMultiTraining;
import ch.opentrainingcenter.gui.model.GMultiTraining;
import ch.opentrainingcenter.gui.model.GTraining;

@Stateless
public class GTrainingService {
    @Inject
    private TrainingService service;

    @Inject
    private GpsFileService fileService;

    public GTraining loadTraining(final long dbId) {
        final Training training = service.findFullTraining(dbId);
        return new GTraining(training, training.getTrackPoints());
    }

    public GTraining storeGpsFile(final InputStream stream) {
        return new GTraining(fileService.convertAndStoreGpsFile(stream));
    }

    public GMultiTraining loadMultiTrainings(final Collection<Long> ids) {
        final List<Training> trainings = new ArrayList<>();
        ids.forEach((final Long id) -> trainings.add(service.find(Training.class, id)));
        return ConvertMultiTraining.convert(trainings);
    }
}
