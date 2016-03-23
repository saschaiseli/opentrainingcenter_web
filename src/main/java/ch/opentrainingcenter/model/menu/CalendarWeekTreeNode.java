package ch.opentrainingcenter.model.menu;

import org.primefaces.model.DefaultTreeNode;

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
        getChildren().add(new TrainingChild(training));
    }
}
