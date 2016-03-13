package ch.opentrainingcenter.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.opentrainingcenter.model.Training;
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
    private TrainingServiceBean trainingService;
    private Training training;

    @Deployment
    public static WebArchive createDeployment() {
        final File garminFitFile = new File("src/main/webapp/WEB-INF/lib", "fit_16.60.0.jar");
        final WebArchive archive = ShrinkWrap.create(WebArchive.class).addClasses(TrainingServiceBean.class, DistanceHelper.class,
                ConvertGarminSemicircles.class, ConvertFitEJB.class, TrainingListener.class).addPackage(Training.class.getPackage()).addAsLibraries(
                        garminFitFile).addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
        return archive;
    }

    @Before
    public void setUp() throws FileNotFoundException {
        training = service.convert(new FileInputStream(new File(FOLDER, "2014_09_11.fit")));
    }

    @Test
    public void testReadWrite() throws FileNotFoundException {
        final int id = trainingService.doSave(training);
        assertTrue("ID must be greater than 0", id > 0);

        final Training trainingFromDb = trainingService.load(id);
        assertNotNull(trainingFromDb);
    }

}
