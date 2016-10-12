package ch.opentrainingcenter.business.service;

import java.io.InputStream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.joda.time.DateTime;

import ch.opentrainingcenter.business.dbaccess.AthleteService;
import ch.opentrainingcenter.business.dbaccess.TrainingService;
import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.business.service.fileconverter.fit.ConvertFitEJB;

@Stateless
public class GpsFileService {
    @Inject
    private ConvertFitEJB convert;

    @Inject
    private TrainingService service;

    @Inject
    private AthleteService aService;

    public Training convertAndStoreGpsFile(final InputStream gpsFile, final long athleteId) {
        final Training training = convert.convert(gpsFile);
        training.setDateOfImport(DateTime.now().toDate());
        final Athlete athlete = aService.find(Athlete.class, athleteId);
        training.setAthlete(athlete);
        service.doSave(training);
        return training;
    }
}
