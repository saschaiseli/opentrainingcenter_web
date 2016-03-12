package ch.opentrainingcenter.service.fileconverter.fit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.naming.NamingException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.opentrainingcenter.model.LapInfo;
import ch.opentrainingcenter.model.Sport;
import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.helper.DistanceHelper;

@RunWith(Arquillian.class)
public class ConvertFitEJBTest {

    @Inject
    ConvertFitEJB service;

    @Deployment
    public static WebArchive createDeployment() {
        final File garminFitFile = new File("src/main/webapp/WEB-INF/lib", "fit_16.60.0.jar");
        final WebArchive jar = ShrinkWrap.create(WebArchive.class).addClasses(DistanceHelper.class, ConvertGarminSemicircles.class, ConvertFitEJB.class,
                TrainingListener.class).addPackage(Training.class.getPackage()).addAsLibraries(garminFitFile).addAsManifestResource(EmptyAsset.INSTANCE,
                        "beans.xml");
        return jar;
    }

    @Before
    public void setUp() {
        Locale.setDefault(Locale.GERMAN);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));
    }

    @Test
    public void testActivityConvertMit2Runden() {
        final File file = new File("src/test/resources/fit", "2_runden.fit");
        final File file2 = new File("");
        System.out.println("file: " + file2.getAbsolutePath());
        Training training;
        try {
            training = service.convert(new FileInputStream(file));
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(file2.getAbsolutePath());
        }

        assertNotNull(training);
        assertNull("Ist null, da dieser Timestamp erst vom importer gesetzt", training.getDateOfImport());

        final List<LapInfo> lapInfos = training.getLapInfos();
        assertEquals(2, lapInfos.size());

        final LapInfo lap1 = lapInfos.get(0);
        assertEquals(0, lap1.getLap());
        assertEquals(0, lap1.getStart());
        assertEquals(5495, lap1.getEnd());
        assertEquals(1959000, lap1.getTime());
        assertEquals(DistanceHelper.calculatePace(5495, 1959, Sport.RUNNING), lap1.getPace());
        assertEquals(DistanceHelper.calculatePace(5495, 1959, Sport.BIKING), lap1.getGeschwindigkeit());

        final LapInfo lap2 = lapInfos.get(1);
        assertEquals(1, lap2.getLap());
        assertEquals(5495, lap2.getStart());
        assertEquals(10499, lap2.getEnd()); // kleiner rundungsfehler
        assertEquals(1745000, lap2.getTime());
        assertEquals(DistanceHelper.calculatePace(10500 - 5495, lap2.getTime() / 1000, Sport.RUNNING), lap2.getPace());
        assertEquals(DistanceHelper.calculatePace(10500 - 5495, lap2.getTime() / 1000, Sport.BIKING), lap2.getGeschwindigkeit());
    }

    @Test
    public void testGetFilePrefix() throws NamingException {
        // final ConvertFitEJB service = (ConvertFitEJB)
        // ctx.lookup("java:global/classes/ConvertFitEJB");
        System.out.println("ysdfsdfsdfsdf");
        assertEquals("fit", service.getFilePrefix());
    }

}
