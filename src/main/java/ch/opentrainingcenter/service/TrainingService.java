package ch.opentrainingcenter.service;

import ch.opentrainingcenter.model.Training;

public interface TrainingService extends RepositoryService<Training> {

    // List<Training> getTraining(final Athlete athlete);

    Training findFullTraining(int id);

}
