package ch.opentrainingcenter.service;

import java.util.List;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.model.Training;

public interface TrainingService {

    List<Training> getTraining(final Athlete athlete);

    void doSave(Training training);

}
