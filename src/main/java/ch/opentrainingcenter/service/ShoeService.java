package ch.opentrainingcenter.service;

import java.util.Collection;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.model.Shoe;

public interface ShoeService extends RepositoryService<Shoe> {

    Collection<Shoe> getShoesByAthlete(Athlete athlete);

    int getUsedMeters(Shoe shoe);
}
