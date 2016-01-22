package ch.opentrainingcenter.util;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
public class Resources {
    @Produces
    @PersistenceContext
    private EntityManager em;

    @Produces
    public Logger produceLog(final InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName(), "messages");
    }

}
