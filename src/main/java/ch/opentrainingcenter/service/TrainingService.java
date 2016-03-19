package ch.opentrainingcenter.service;

import java.util.List;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.model.Training;

public interface TrainingService extends RepositoryService<Training> {

    List<Training> findTrainingByAthlete(final Athlete athlete);

    Training findFullTraining(int id);

}
