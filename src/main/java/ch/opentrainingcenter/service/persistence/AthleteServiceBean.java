package ch.opentrainingcenter.service.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.service.AthleteService;

@Stateless
public class AthleteServiceBean implements AthleteService {

    @PersistenceContext(unitName = "otc")
    private EntityManager entityManager;

    @Override
    public Athlete doSave(final Athlete athlete) {
        entityManager.persist(athlete);
        return athlete;
    }

    @Override
    public Athlete find(final int id) {
        return entityManager.find(Athlete.class, id);
    }

    @Override
    public Athlete update(final Athlete athlete) {
        return entityManager.merge(athlete);
    }

    @Override
    public void remove(final Athlete athlete) {
        final Athlete managed = find(athlete.getId());
        entityManager.remove(managed);
    }

}
