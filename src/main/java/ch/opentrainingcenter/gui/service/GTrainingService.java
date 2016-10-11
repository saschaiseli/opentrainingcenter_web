package ch.opentrainingcenter.gui.service;

import java.io.InputStream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ch.opentrainingcenter.business.dbaccess.TrainingService;
import ch.opentrainingcenter.business.service.GpsFileService;
import ch.opentrainingcenter.gui.model.GTraining;

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
