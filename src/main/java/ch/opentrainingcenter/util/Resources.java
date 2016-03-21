package ch.opentrainingcenter.util;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
public class Resources {
    @Produces
    @PersistenceContext(unitName = "otc")
    private EntityManager em;

    // @Produces
    // public Logger produceLog(final InjectionPoint injectionPoint) {
    // return
    // LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    // }

    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
