package ch.opentrainingcenter.service;

import ch.opentrainingcenter.model.Training;

public interface TrainingService {

    // List<Training> getTraining(final Athlete athlete);

    int doSave(Training training);

    Training findFullTraining(int id);
}
