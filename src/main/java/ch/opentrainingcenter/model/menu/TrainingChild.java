package ch.opentrainingcenter.model.menu;

import org.primefaces.model.DefaultTreeNode;

import ch.opentrainingcenter.model.Training;

public class TrainingChild extends DefaultTreeNode {

    private static final long serialVersionUID = 1L;
    private final Training training;

    public TrainingChild(final Training training) {
        super(training.getId() + " " + training.getFileName());
        this.training = training;
    }
}
