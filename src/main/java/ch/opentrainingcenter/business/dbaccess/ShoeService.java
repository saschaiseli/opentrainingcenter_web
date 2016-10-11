package ch.opentrainingcenter.business.dbaccess;

import java.util.Collection;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Shoe;

public interface ShoeService extends RepositoryService<Shoe> {

    Collection<Shoe> getShoesByAthlete(Athlete athlete);

    int getUsedMeters(Shoe shoe);
}
