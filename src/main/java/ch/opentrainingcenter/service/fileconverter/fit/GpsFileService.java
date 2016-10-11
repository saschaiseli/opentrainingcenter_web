package ch.opentrainingcenter.service.fileconverter.fit;

import java.io.InputStream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.joda.time.DateTime;

import ch.opentrainingcenter.gui.controller.MyApplicationScope;
import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.TrainingService;

@Stateless
public class GpsFileService {
    @Inject
    private ConvertFitEJB convert;

    @Inject
    private TrainingService service;
    @Inject
    private MyApplicationScope scope;

    public Training convertAndStoreGpsFile(final InputStream gpsFile) {
        final Training training = convert.convert(gpsFile);
        training.setDateOfImport(DateTime.now().toDate());
        training.setAthlete(scope.getApplicationUser());
        service.doSave(training);
        return training;
    }
}
