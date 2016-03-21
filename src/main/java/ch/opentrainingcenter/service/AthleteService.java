package ch.opentrainingcenter.service;

import ch.opentrainingcenter.model.Athlete;

public interface AthleteService extends RepositoryService<Athlete> {

    Athlete findByEmail(String dummyEmail);

}
