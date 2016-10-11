package ch.opentrainingcenter.gui.model.menu;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import ch.opentrainingcenter.gui.model.GTraining;

public class CalendarWeekTreeNode extends DefaultTreeNode {

    private static final long serialVersionUID = 1L;
    private final int calWeek;

    public CalendarWeekTreeNode(final int calWeek) {
        super("Week " + calWeek);
        this.calWeek = calWeek;
    }

    public int getCalendarWeek() {
        return calWeek;
    }

    public void addChild(final GTraining training) {
        int index = 0;
        for (final TreeNode item : getChildren()) {
            final TrainingChild tr = (TrainingChild) item;
            if (tr.getTraining().getStartInMillis() > training.getStartInMillis()) {
                index++;
            } else {
                break;
            }
        }
        getChildren().add(index, new TrainingChild(training));
    }

    public List<GTraining> getTrainings() {
        final List<GTraining> trainings = new ArrayList<>();
        for (final TreeNode treeNode : getChildren()) {
            trainings.add(((TrainingChild) treeNode.getData()).getTraining());
        }
        return trainings;
    }
}
