package ch.opentrainingcenter.service.persistence;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.model.Shoe;
import ch.opentrainingcenter.service.ShoeService;

@Stateless
public class ShoeServiceBean extends RepositoryServiceBean<Shoe> implements ShoeService {

    @PersistenceContext(unitName = "otc")
    private EntityManager entityManager;

    @Override
    public Collection<Shoe> getShoesByAthlete(final Athlete athlete) {
        final Query query = entityManager.createNamedQuery("Shoe.getShoesByAthlete");
        query.setParameter("athlete", athlete);
        @SuppressWarnings("unchecked")
        final List<Shoe> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public int getUsedMeters(final Shoe shoe) {
        final Query query = entityManager.createNamedQuery("Shoe.getKilometers");
        query.setParameter("shoe", shoe);
        final Object value = query.getSingleResult();
        int result;
        if (value != null) {
            result = Integer.valueOf(value.toString());
        } else {
            result = 0;
        }
        return result;
    }

}
