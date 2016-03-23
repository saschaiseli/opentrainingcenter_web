package ch.opentrainingcenter.model.menu;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import ch.opentrainingcenter.model.Training;

public class CalendarWeekTreeNode extends DefaultTreeNode {

    private static final long serialVersionUID = 1L;
    private final int calWeek;

    public CalendarWeekTreeNode(final int calWeek) {
        super("KW_" + calWeek);
        this.calWeek = calWeek;
    }

    public int getCalendarWeek() {
        return calWeek;
    }

    public void addChild(final Training training) {
        int index = 0;
        for (final TreeNode item : getChildren()) {
            final TrainingChild tr = (TrainingChild) item;
            if (tr.getTraining().getDateOfStart().getTime() > training.getDateOfStart().getTime()) {
                index++;
            } else {
                break;
            }
        }
        getChildren().add(index, new TrainingChild(training));
    }
}
