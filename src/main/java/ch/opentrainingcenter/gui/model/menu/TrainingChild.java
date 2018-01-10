package ch.opentrainingcenter.gui.model.menu;

import org.primefaces.model.DefaultTreeNode;

import ch.opentrainingcenter.gui.model.GTraining;

public class TrainingChild extends DefaultTreeNode {

    private static final long serialVersionUID = 1L;
    private final GTraining training;

    public TrainingChild(final GTraining training) {
        super(training.getStartDatum());
        this.training = training;
    }

    public GTraining getTraining() {
        return training;
    }
}
