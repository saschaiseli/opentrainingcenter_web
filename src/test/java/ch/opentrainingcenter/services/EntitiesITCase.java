package ch.opentrainingcenter.services;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.opentrainingcenter.model.Athlete;

@RunWith(Arquillian.class)
public class EntitiesITCase {

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Athlete.class.getPackage()).addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void test() throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException,
            HeuristicRollbackException {
        utx.begin();
        em.joinTransaction();

        final String allAthlete = "select a from Athlete a";
        final List<Athlete> resultList = em.createQuery(allAthlete, Athlete.class).getResultList();

        assertTrue("Es muss ein Resultate vorhanden sein", resultList.size() >= 1);
        utx.commit();
    }
}
