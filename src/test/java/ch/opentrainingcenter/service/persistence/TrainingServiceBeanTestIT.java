package ch.opentrainingcenter.service.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.model.CommonTransferFactory;
import ch.opentrainingcenter.model.LapInfo;
import ch.opentrainingcenter.model.Tracktrainingproperty;
import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.AthleteService;
import ch.opentrainingcenter.service.RepositoryService;
import ch.opentrainingcenter.service.TrainingService;
import ch.opentrainingcenter.service.fileconverter.fit.ConvertFitEJB;
import ch.opentrainingcenter.service.fileconverter.fit.ConvertGarminSemicircles;
import ch.opentrainingcenter.service.fileconverter.fit.TrainingListener;
import ch.opentrainingcenter.service.helper.DistanceHelper;

@RunWith(Arquillian.class)
public class TrainingServiceBeanTestIT {

    private static final String FOLDER = "src/test/resources/fit";
    @Inject
    private ConvertFitEJB service;

    @Inject
    private TrainingService trainingService;
    @Inject
    AthleteService athleteService;

    private Training training;
    private Athlete athlete;

    @Deployment
    public static WebArchive createDeployment() {
        final File garminFitFile = new File("src/main/webapp/WEB-INF/lib", "fit_16.60.0.jar");
        final WebArchive archive = ShrinkWrap.create(WebArchive.class).addClasses(RepositoryService.class, RepositoryServiceBean.class, AthleteService.class,
                AthleteServiceBean.class, TrainingService.class, TrainingServiceBean.class, DistanceHelper.class, ConvertGarminSemicircles.class,
                ConvertFitEJB.class, TrainingListener.class).addPackage(Training.class.getPackage()).addAsLibraries(garminFitFile).addAsManifestResource(
                        EmptyAsset.INSTANCE, "beans.xml");
        archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");

        final MavenResolverSystem resolver = Maven.resolver();
        final File[] files = resolver.loadPomFromFile("pom.xml").importDependencies(ScopeType.COMPILE, ScopeType.RUNTIME).resolve().withTransitivity().asFile();
        archive.addAsLibraries(files);
        return archive;
    }

    @Before
    public void setUp() throws FileNotFoundException {
        training = service.convert(new FileInputStream(new File(FOLDER, "2014_09_11.fit")));
        athlete = CommonTransferFactory.createAthlete("firstName", "lastName", "mail@opentrainingceter.ch", "password");
        athleteService.doSave(athlete);

        training.setAthlete(athlete);
    }

    @After
    public void tearDown() {
        athleteService.remove(Athlete.class, athlete.getId());
    }

    @Test
    public void testReadWriteTraining() throws FileNotFoundException {
        training = trainingService.doSave(training);
        assertTrue("ID must be greater than 0", training.getId() > 0);

        final Training trainingFromDb = trainingService.find(Training.class, training.getId());

        assertNotNull(trainingFromDb.getAthlete());
    }

    @Test
    public void testReadWriteFullTraining() throws FileNotFoundException {
        training = trainingService.doSave(training);
        assertTrue("ID must be greater than 0", training.getId() > 0);

        final Training trainingFromDb = trainingService.findFullTraining(training.getId());

        assertPoints(training.getTrackPoints(), trainingFromDb.getTrackPoints());
        assertLaps(training.getLapInfos(), trainingFromDb.getLapInfos());
        assertNotNull(trainingFromDb.getAthlete());
    }

    private void assertLaps(final List<LapInfo> expectedLap, final List<LapInfo> dbLap) {
        for (int i = 0; i < expectedLap.size(); i++) {
            final LapInfo expected = expectedLap.get(i);
            final LapInfo db = dbLap.get(i);
            assertEquals(expected.getTraining().getId(), db.getTraining().getId());
            assertEquals("ID", expected.getId(), db.getId());
            assertEquals("Time", expected.getTime(), db.getTime());
        }
    }

    private void assertPoints(final List<Tracktrainingproperty> expectedPoints, final List<Tracktrainingproperty> dbPoints) {
        for (int i = 0; i < expectedPoints.size(); i++) {
            final Tracktrainingproperty expected = expectedPoints.get(i);
            final Tracktrainingproperty db = dbPoints.get(i);
            assertEquals(expected.getTraining().getId(), db.getTraining().getId());
            assertEquals("ID", expected.getId(), db.getId());
            assertEquals("Longitude", expected.getLongitude(), db.getLongitude());
            assertEquals("Latitude", expected.getLatitude(), db.getLatitude());
            assertEquals("Distance ", expected.getDistance(), db.getDistance(), 0.0000001);
            assertEquals("Time", expected.getZeit(), db.getZeit());
        }
    }

}