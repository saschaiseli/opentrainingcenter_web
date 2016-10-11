package ch.opentrainingcenter.business.dbaccess;

import java.util.List;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.Training;

public interface TrainingService extends RepositoryService<Training> {

    List<Training> findTrainingByAthlete(final Athlete athlete);

    Training findFullTraining(long id);

}
