package ch.opentrainingcenter.model.menu;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;

import org.joda.time.DateTime;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import ch.opentrainingcenter.model.Training;

@Model
@SessionScoped
public class YearTreeNode extends DefaultTreeNode {

    private static final long serialVersionUID = 1L;
    private final int year;

    public YearTreeNode(final int year) {
        super(Integer.valueOf(year));
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void addChild(final Training training) {
        final Date dateOfStart = training.getDateOfStart();
        final DateTime dt = new DateTime(dateOfStart.getTime());
        final int weekOfWeekyear = dt.getWeekOfWeekyear();

        final List<TreeNode> kwsOfYear = getChildren();
        boolean added = false;
        for (final TreeNode kwOfYear : kwsOfYear) {
            final CalendarWeekTreeNode kw = (CalendarWeekTreeNode) kwOfYear;
            if (kw.getCalendarWeek() == weekOfWeekyear) {
                kw.getChildren().add(new DefaultTreeNode(training));
                added = true;
                break;
            }
        }
        if (!added) {
            // KW gibt es nicht
            final CalendarWeekTreeNode cwNode = new CalendarWeekTreeNode(weekOfWeekyear);
            cwNode.addChild(training);
            getChildren().add(cwNode);

        }
    }

}
