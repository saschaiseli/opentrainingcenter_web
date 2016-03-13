package ch.opentrainingcenter.service;

import javax.persistence.EntityManager;

import ch.opentrainingcenter.model.Training;

public interface TrainingService {

    // List<Training> getTraining(final Athlete athlete);

    int doSave(Training training);

    Training load(int id);

    void setEm(final EntityManager em);
}
