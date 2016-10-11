package ch.opentrainingcenter.business.service;

import java.io.InputStream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.joda.time.DateTime;

import ch.opentrainingcenter.business.dbaccess.TrainingService;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.business.service.fileconverter.fit.ConvertFitEJB;
import ch.opentrainingcenter.gui.controller.MyApplicationScope;

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
