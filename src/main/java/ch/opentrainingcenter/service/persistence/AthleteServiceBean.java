package ch.opentrainingcenter.service.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.service.AthleteService;

@Stateless
public class AthleteServiceBean extends RepositoryServiceBean<Athlete> implements AthleteService {

    @PersistenceContext(unitName = "otc")
    private EntityManager entityManager;

}
