package ch.opentrainingcenter.business.service;

import java.io.InputStream;
import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
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

    @Resource
    private SessionContext sessionContext;

    public Training convertAndStoreGpsFile(final InputStream gpsFile) {
        final Training training = convert.convert(gpsFile);
        training.setDateOfImport(DateTime.now().toDate());

        final Principal principal = sessionContext.getCallerPrincipal();
        String email = principal.getName();
        final Athlete athlete = aService.findByEmail(email);
        training.setAthlete(athlete);
        service.doSave(training);
        return training;
    }
}
