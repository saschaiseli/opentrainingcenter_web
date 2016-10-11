package ch.opentrainingcenter.gui.service;

import java.io.InputStream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.service.TrainingService;
import ch.opentrainingcenter.service.fileconverter.fit.GpsFileService;

@Stateless
public class GTrainingService {
    @Inject
    private TrainingService service;

    @Inject
    private GpsFileService fileService;

    public GTraining loadTraining(final long dbId) {
        return new GTraining(service.findFullTraining(dbId));
    }

    public GTraining storeGpsFile(final InputStream stream) {
        return new GTraining(fileService.convertAndStoreGpsFile(stream));
    }
}
