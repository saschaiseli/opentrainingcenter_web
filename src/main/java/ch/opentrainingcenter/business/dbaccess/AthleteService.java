package ch.opentrainingcenter.business.dbaccess;

import ch.opentrainingcenter.business.domain.Athlete;

public interface AthleteService extends RepositoryService<Athlete> {

    Athlete findByEmail(String dummyEmail);

}
