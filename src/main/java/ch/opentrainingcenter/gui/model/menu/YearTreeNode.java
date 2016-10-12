package ch.opentrainingcenter.gui.model.menu;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import ch.opentrainingcenter.gui.model.GTraining;

public class YearTreeNode extends DefaultTreeNode {

    private static final long serialVersionUID = 1L;
    private final int year;
    private final String kw;

    protected YearTreeNode(final int year, final String kw) {
        super(Integer.valueOf(year));
        this.year = year;
        this.kw = kw;
    }

    public int getYear() {
        return year;
    }

    public void addChild(final GTraining training) {
        final Date dateOfStart = new Date(training.getStartInMillis());
        final Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(dateOfStart);
        final int monthTraining = cal.get(Calendar.MONTH) + 1;
        final List<TreeNode> monthsOfYear = getChildren();
        boolean added = false;
        int index = 0;
        for (final TreeNode monthOfYear : monthsOfYear) {
            final MonthTreeNode monthNode = (MonthTreeNode) monthOfYear;
            if (monthNode.getMonth() == monthTraining) {
                monthNode.addChild(training);
                added = true;
                break;
            } else if (monthNode.getMonth() > monthTraining) {
                index++;
            }
        }
        if (!added) {
            // Monat gibt es nicht
            final MonthTreeNode monthNode = new MonthTreeNode(monthTraining, kw);
            monthNode.addChild(training);
            getChildren().add(index, monthNode);

        }
    }
}
