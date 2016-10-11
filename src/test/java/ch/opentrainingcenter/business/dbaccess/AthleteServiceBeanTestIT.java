package ch.opentrainingcenter.business.dbaccess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

import ch.opentrainingcenter.business.dbaccess.AthleteService;
import ch.opentrainingcenter.business.dbaccess.RepositoryService;
import ch.opentrainingcenter.business.dbaccess.impl.AthleteServiceBean;
import ch.opentrainingcenter.business.dbaccess.impl.RepositoryServiceBean;
import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.CommonTransferFactory;
import ch.opentrainingcenter.business.domain.Health;

@RunWith(Arquillian.class)
public class AthleteServiceBeanTestIT {

    @Inject
    private AthleteService athleteService;

    private Athlete athlete;

    private Date now;

    @Deployment
    public static WebArchive createDeployment() {
        final WebArchive archive = ShrinkWrap.create(WebArchive.class).addClasses(RepositoryService.class, RepositoryServiceBean.class, AthleteService.class,
                AthleteServiceBean.class).addPackage(Athlete.class.getPackage()).addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");

        final MavenResolverSystem resolver = Maven.resolver();
        final File[] files = resolver.loadPomFromFile("pom.xml").importDependencies(ScopeType.COMPILE, ScopeType.RUNTIME).resolve().withTransitivity().asFile();
        archive.addAsLibraries(files);
        return archive;
    }

    @Before
    public void setUp() {
        athlete = CommonTransferFactory.createAthlete("first name", "last name", "test@opentrainingcenter.ch", "abc");
        athleteService.doSave(athlete);
        now = (new DateTime(1_000_000)).toDate();
    }

    @After
    public void tearDown() {
        athleteService.remove(Athlete.class, athlete.getId());
    }

    @Test
    public void testFindAthlete() {
        assertTrue("DB ID must be greater than 0", athlete.getId() > 0);

        final Athlete athleteFromDb = athleteService.find(Athlete.class, athlete.getId());
        assertEquals("FirstName", athlete.getFirstName(), athleteFromDb.getFirstName());
        assertEquals("LastName", athlete.getLastName(), athleteFromDb.getLastName());
        assertEquals("Email", athlete.getEmail(), athleteFromDb.getEmail());
    }

    @Test
    public void testFindAthlete_notFound() {
        final Athlete athleteFromDb = athleteService.find(Athlete.class, Integer.MAX_VALUE);

        assertNull(athleteFromDb);
    }

    @Test
    public void testUpdateAthlete() {
        athlete.setMaxHeartRate(180);

        final Athlete updated = athleteService.update(athlete);

        assertEquals(180, updated.getMaxHeartRate());
    }

    @Test
    public void testUpdateAthleteWithHeart() {
        final Set<Health> healths = new HashSet<>();
        healths.add(CommonTransferFactory.createHealth(athlete, 68, 55, now));
        athlete.setHealths(healths);

        final Athlete updated = athleteService.update(athlete);

        final Health health = updated.getHealths().iterator().next();
        assertEquals(68, health.getWeight().intValue());
        assertEquals(55, health.getCardio().intValue());
        assertEquals(now, health.getDateofmeasure());
    }

    @Test
    public void testFindByEmail_notFound() {
        assertNull("Athlete not found", athleteService.findByEmail("blabla"));
    }

    @Test
    public void testFindByEmail_Found() {
        assertEquals("Athlete found", athlete.getId(), athleteService.findByEmail(athlete.getEmail()).getId());
    }

    @Test
    public void testUpdateAthlete_NotFound() {
        final Athlete other = CommonTransferFactory.createAthlete("2first name", "2last name", "2test@opentrainingcenter.ch", "2abc");
        other.setId(42);
        other.setMaxHeartRate(195);

        final Athlete updated = athleteService.update(other);

        assertEquals(195, updated.getMaxHeartRate());

        assertNull(athleteService.find(Athlete.class, 42));
        assertNotNull(athleteService.find(Athlete.class, updated.getId()));
    }
}
