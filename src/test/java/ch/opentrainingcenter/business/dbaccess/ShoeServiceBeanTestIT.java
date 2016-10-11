package ch.opentrainingcenter.business.dbaccess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Date;

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
import ch.opentrainingcenter.business.dbaccess.ShoeService;
import ch.opentrainingcenter.business.dbaccess.TrainingService;
import ch.opentrainingcenter.business.dbaccess.impl.AthleteServiceBean;
import ch.opentrainingcenter.business.dbaccess.impl.RepositoryServiceBean;
import ch.opentrainingcenter.business.dbaccess.impl.ShoeServiceBean;
import ch.opentrainingcenter.business.dbaccess.impl.TrainingServiceBean;
import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.CommonTransferFactory;
import ch.opentrainingcenter.business.domain.Shoe;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.business.service.fileconverter.fit.ConvertFitEJB;
import ch.opentrainingcenter.business.service.fileconverter.fit.ConvertGarminSemicircles;
import ch.opentrainingcenter.business.service.fileconverter.fit.TrainingListener;

@RunWith(Arquillian.class)
public class ShoeServiceBeanTestIT {

    private static final String FOLDER = "src/test/resources/fit";

    @Inject
    private ConvertFitEJB service;
    @Inject
    private TrainingService trainingService;
    @Inject
    private AthleteService athleteService;
    @Inject
    private ShoeService shoeService;

    private Training training;
    private Athlete athlete;
    private Shoe shoe;

    private Date now;

    @Deployment
    public static WebArchive createDeployment() {
        final File garminFitFile = new File("src/main/webapp/WEB-INF/lib", "fit_16.60.0.jar");
        final WebArchive archive = ShrinkWrap.create(WebArchive.class).addClasses(RepositoryService.class, RepositoryServiceBean.class, ShoeService.class,
                ShoeServiceBean.class, AthleteService.class, AthleteServiceBean.class, TrainingService.class, TrainingServiceBean.class, DistanceHelper.class,
                ConvertGarminSemicircles.class, ConvertFitEJB.class, TrainingListener.class).addPackage(Training.class.getPackage()).addAsLibraries(
                        garminFitFile).addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");

        final MavenResolverSystem resolver = Maven.resolver();
        final File[] files = resolver.loadPomFromFile("pom.xml").importDependencies(ScopeType.COMPILE, ScopeType.RUNTIME).resolve().withTransitivity().asFile();
        archive.addAsLibraries(files);
        return archive;
    }

    @Before
    public void setUp() throws FileNotFoundException {
        training = service.convert(new FileInputStream(new File(FOLDER, "2014_09_11.fit")));
        training.setDateOfImport(DateTime.now().toDate());
        athlete = CommonTransferFactory.createAthlete("firstName", "lastName", "mail@opentrainingceter.ch", "password");
        athleteService.doSave(athlete);
        training.setAthlete(athlete);
        now = DateTime.now().toDate();
        shoe = CommonTransferFactory.createShoe(athlete, "shoeName", "image", 100, now);
        shoe = shoeService.doSave(shoe);
    }

    @After
    public void tearDown() {
        athleteService.remove(Athlete.class, athlete.getId());
    }

    @Test
    public void testFindShoe() {
        final Shoe other = CommonTransferFactory.createShoe(athlete, "shoeName2", "image2", 102, now);
        shoeService.doSave(other);

        final Shoe shoeFromDb = shoeService.find(Shoe.class, other.getId());

        assertNotNull(shoeFromDb);

        shoeService.remove(Shoe.class, shoeFromDb.getId());
    }

    @Test
    public void testReadWriteShoe() {

        assertTrue("ID must be greater than 0", shoe.getId() > 0);

        shoe.setPreis(42);

        final Shoe other = shoeService.update(shoe);
        assertEquals(42, other.getPreis());
        assertEquals(shoe.getId(), other.getId());
        System.out.println(shoe.getId());
    }

    @Test
    public void testLoadByAthlete() {
        // shoe = shoeService.doSave(shoe);
        final Collection<Shoe> shoes = shoeService.getShoesByAthlete(athlete);
        final Shoe other = shoes.iterator().next();
        assertEquals(shoe.getId(), other.getId());
    }

    @Test
    public void testShoeKilometers() {
        // shoe = shoeService.doSave(shoe);
        training.setShoe(shoe);

        trainingService.update(training);

        final int meter = shoeService.getUsedMeters(shoe);

        assertEquals(training.getLaengeInMeter(), meter);
    }

    @Test
    public void testShoeKilometers_ShoeNotUsed() {
        // shoeService.doSave(shoe);

        final Shoe other = CommonTransferFactory.createShoe(athlete, "shoeName2", "image2", 102, now);
        shoeService.doSave(other);

        training.setShoe(other);

        trainingService.update(training);

        final int meter = shoeService.getUsedMeters(shoe);

        assertEquals(0, meter);
    }
}
