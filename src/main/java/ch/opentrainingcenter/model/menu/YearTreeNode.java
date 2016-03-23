package ch.opentrainingcenter.model.menu;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.helper.TimeHelper;

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
        final int weekOfWeekyear = TimeHelper.getKalenderWoche(dateOfStart, Locale.getDefault());
        final List<TreeNode> kwsOfYear = getChildren();
        boolean added = false;
        int index = 0;
        for (final TreeNode kwOfYear : kwsOfYear) {
            final CalendarWeekTreeNode kw = (CalendarWeekTreeNode) kwOfYear;
            if (kw.getCalendarWeek() == weekOfWeekyear) {
                kw.addChild(training);
                added = true;
                break;
            } else if (kw.getCalendarWeek() > weekOfWeekyear) {
                index++;
            }
        }
        if (!added) {
            // KW gibt es nicht
            final CalendarWeekTreeNode cwNode = new CalendarWeekTreeNode(weekOfWeekyear);
            cwNode.addChild(training);
            getChildren().add(index, cwNode);

        }
    }

}
