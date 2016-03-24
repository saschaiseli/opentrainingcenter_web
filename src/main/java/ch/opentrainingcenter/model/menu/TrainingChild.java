package ch.opentrainingcenter.model.menu;

import org.primefaces.model.DefaultTreeNode;

import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.helper.TimeHelper;

public class TrainingChild extends DefaultTreeNode {

    private static final long serialVersionUID = 1L;
    private final Training training;

    public TrainingChild(final Training training) {
        super(TimeHelper.convertDateToString(training.getDateOfStart()));
        this.training = training;
    }

    public Training getTraining() {
        return training;
    }
}
