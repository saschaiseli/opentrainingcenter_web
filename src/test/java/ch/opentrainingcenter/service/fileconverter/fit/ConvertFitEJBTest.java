package ch.opentrainingcenter.service.fileconverter.fit;

import static org.junit.Assert.assertEquals;

import javax.naming.NamingException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.garmin.fit.Mesg;
import com.garmin.fit.MesgListener;
import com.garmin.fit.MesgWithEvent;
import com.garmin.fit.RecordMesg;
import com.garmin.fit.SessionMesg;

import ch.opentrainingcenter.model.Training;

@RunWith(Arquillian.class)
public class ConvertFitEJBTest {

    ConvertFitEJB service = new ConvertFitEJB();

    @Deployment
    public static JavaArchive createDeployment() {

        final JavaArchive jar = ShrinkWrap.create(JavaArchive.class).addClasses(ConvertFitEJB.class, TrainingListener.class).addPackage(MesgListener.class
                .getPackage()).addPackage(Training.class.getPackage()).addClasses(RecordMesg.class, com.garmin.fit.LapMesg.class, Mesg.class,
                        MesgListener.class, SessionMesg.class, MesgWithEvent.class).addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return jar;
    }

    @Before
    public void setUp() {
        // ec = EJBContainer.createEJBContainer();
        // ctx = ec.getContext();
    }

    @Test
    public void testConvert() {
    }

    @Test
    public void testGetFilePrefix() throws NamingException {
        // final ConvertFitEJB service = (ConvertFitEJB)
        // ctx.lookup("java:global/classes/ConvertFitEJB");
        System.out.println("ysdfsdfsdfsdf");
        assertEquals("fit", service.getFilePrefix());
    }

}
