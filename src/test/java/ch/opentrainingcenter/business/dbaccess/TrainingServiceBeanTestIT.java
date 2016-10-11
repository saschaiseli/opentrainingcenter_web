package ch.opentrainingcenter.business.dbaccess;

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
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.opentrainingcenter.business.DistanceHelper;
import ch.opentrainingcenter.business.dbaccess.AthleteService;
import ch.opentrainingcenter.business.dbaccess.RepositoryService;
import ch.opentrainingcenter.business.dbaccess.TrainingService;
import ch.opentrainingcenter.business.dbaccess.impl.AthleteServiceBean;
import ch.opentrainingcenter.business.dbaccess.impl.RepositoryServiceBean;
import ch.opentrainingcenter.business.dbaccess.impl.TrainingServiceBean;
import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.CommonTransferFactory;
import ch.opentrainingcenter.business.domain.LapInfo;
import ch.opentrainingcenter.business.domain.Tracktrainingproperty;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.business.service.fileconverter.fit.ConvertFitEJB;
import ch.opentrainingcenter.business.service.fileconverter.fit.ConvertGarminSemicircles;
import ch.opentrainingcenter.business.service.fileconverter.fit.TrainingListener;

@RunWith(Arquillian.class)
public class TrainingServiceBeanTestIT {

	private static final String FOLDER = "src/test/resources/fit";
	@Inject
	private ConvertFitEJB service;

	@Inject
	private TrainingService trainingService;
	@Inject
	AthleteService athleteService;

	private Athlete athlete;
	private Training training1;
	private Training training2;
	private Athlete otherAthlete;

	@Deployment
	public static WebArchive createDeployment() {
		final File garminFitFile = new File("src/main/webapp/WEB-INF/lib", "fit_16.60.0.jar");
		final WebArchive archive = ShrinkWrap.create(WebArchive.class)
				.addClasses(RepositoryService.class, RepositoryServiceBean.class, AthleteService.class,
						AthleteServiceBean.class, TrainingService.class, TrainingServiceBean.class,
						DistanceHelper.class, ConvertGarminSemicircles.class, ConvertFitEJB.class,
						TrainingListener.class)
				.addPackage(Training.class.getPackage()).addAsLibraries(garminFitFile)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");

		final MavenResolverSystem resolver = Maven.resolver();
		final File[] files = resolver.loadPomFromFile("pom.xml")
				.importDependencies(ScopeType.COMPILE, ScopeType.RUNTIME).resolve().withTransitivity().asFile();
		archive.addAsLibraries(files);
		return archive;
	}

	@Before
	public void setUp() throws FileNotFoundException {
		training1 = service.convert(new FileInputStream(new File(FOLDER, "2014_09_11.fit")));
		training1.setDateOfImport(DateTime.now().toDate());

		training2 = service.convert(new FileInputStream(new File(FOLDER, "2014_09_09.fit")));
		training2.setDateOfImport(DateTime.now().toDate());

		athlete = CommonTransferFactory.createAthlete("firstName", "lastName", "mail@opentrainingceter.ch", "password");
		athleteService.doSave(athlete);

		otherAthlete = CommonTransferFactory.createAthlete("firstName2", "lastName2", "mail2@opentrainingceter.ch",
				"password");
		athleteService.doSave(otherAthlete);

		training1.setAthlete(athlete);
		training2.setAthlete(athlete);

		training1 = trainingService.doSave(training1);
		training2 = trainingService.doSave(training2);
	}

	@After
	public void tearDown() {
		athleteService.remove(Athlete.class, athlete.getId());
		athleteService.remove(Athlete.class, otherAthlete.getId());
	}

	@Test
	public void testReadWriteTraining() throws FileNotFoundException {
		assertTrue("ID must be greater than 0", training1.getId() > 0);
		assertNotNull(training1.getDateOfImport());

		final Training trainingFromDb = trainingService.find(Training.class, training1.getId());

		assertNotNull(trainingFromDb.getAthlete());
	}

	@Test
	public void testFindTrainingByAthlete_found() throws FileNotFoundException {

		final List<Training> trainings = trainingService.findTrainingByAthlete(athlete);

		assertEquals(2, trainings.size());

		assertTrue("First Training must be first element",
				trainings.get(0).getDateOfStart().getTime() > trainings.get(1).getDateOfStart().getTime());
	}

	@Test
	public void testFindTrainingByAthlete_not_found() throws FileNotFoundException {

		final List<Training> trainings = trainingService.findTrainingByAthlete(otherAthlete);

		assertEquals(0, trainings.size());
	}

	@Test
	public void testReadWriteFullTraining() throws FileNotFoundException {
		assertTrue("ID must be greater than 0", training1.getId() > 0);

		final Training trainingFromDb = trainingService.findFullTraining(training1.getId());

		assertPoints(training1.getTrackPoints(), trainingFromDb.getTrackPoints());
		assertLaps(training1.getLapInfos(), trainingFromDb.getLapInfos());
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

	private void assertPoints(final List<Tracktrainingproperty> expectedPoints,
			final List<Tracktrainingproperty> dbPoints) {
		for (int i = 0; i < expectedPoints.size(); i++) {
			final Tracktrainingproperty expected = expectedPoints.get(i);
			final Tracktrainingproperty db = dbPoints.get(i);
			assertEquals("ID", expected.getId(), db.getId());
			assertEquals("Longitude", expected.getLongitude(), db.getLongitude());
			assertEquals("Latitude", expected.getLatitude(), db.getLatitude());
			assertEquals("Distance ", expected.getDistance(), db.getDistance(), 0.0000001);
			assertEquals("Time", expected.getZeit(), db.getZeit());
		}
	}

}
