package ch.opentrainingcenter.service;

import ch.opentrainingcenter.model.Athlete;

public interface AthleteService {

    public Athlete doSave(Athlete athlete);

    public Athlete find(int id);

    public Athlete update(Athlete athlete);

    public void remove(Athlete athlete);
}
