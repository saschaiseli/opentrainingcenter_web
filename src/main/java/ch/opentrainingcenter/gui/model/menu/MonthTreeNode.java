package ch.opentrainingcenter.gui.model.menu;

import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.service.TimeHelper;

public class MonthTreeNode extends DefaultTreeNode {

    private static final long serialVersionUID = 1L;
    private final int monthTraining;
    private final String kwText;

    protected MonthTreeNode(final int monthTraining, final String kw) {
        super(new DateFormatSymbols().getMonths()[monthTraining - 1]);
        this.monthTraining = monthTraining;
        this.kwText = kw;
    }

    public int getMonth() {
        return monthTraining;
    }

    public void addChild(final GTraining training) {
        final Date dateOfStart = new Date(training.getStartInMillis());
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
            final CalendarWeekTreeNode cwNode = new CalendarWeekTreeNode(kwText, weekOfWeekyear);
            cwNode.addChild(training);
            getChildren().add(index, cwNode);

        }
    }

}
