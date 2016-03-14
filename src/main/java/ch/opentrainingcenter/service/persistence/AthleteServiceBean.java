package ch.opentrainingcenter.service.persistence;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.service.AthleteService;

@Stateless
public class AthleteServiceBean implements AthleteService {

    @Inject
    private EntityManager entityManager;

    @Override
    public void doSave(final Athlete athlete) {
        entityManager.persist(athlete);
    }

}
