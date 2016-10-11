package ch.opentrainingcenter.gui.model.menu;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import ch.opentrainingcenter.gui.model.GTraining;

/**
 * Root
 */
public class MenuTreeNode extends DefaultTreeNode {

    private static final long serialVersionUID = 1L;

    public MenuTreeNode() {
        super("Root", null);
    }

    public void addNode(final GTraining training) {
        final Date dateOfStart = new Date(training.getStartInMillis());
        final DateTime dt = new DateTime(dateOfStart.getTime());
        final int yearNumber = dt.getYear();

        final List<TreeNode> years = getChildren();
        boolean added = false;
        int index = 0;
        for (final TreeNode year : years) {
            final YearTreeNode yearTreeNode = (YearTreeNode) year;
            if (yearTreeNode.getYear() == yearNumber) {
                yearTreeNode.addChild(training);
                added = true;
                break;
            } else if (yearTreeNode.getYear() > yearNumber) {
                index++;
            }
        }
        if (!added) {
            // year does not already exists
            final YearTreeNode trNode = new YearTreeNode(yearNumber);
            trNode.addChild(training);
            getChildren().add(index, trNode);
        }
    }

    public void removeNode(final GTraining training) {
        final List<TreeNode> years = getChildren();
        final Iterator<TreeNode> yearIterator = years.iterator();
        while (yearIterator.hasNext()) {
            final TreeNode nextYear = yearIterator.next();
            final List<TreeNode> cws = nextYear.getChildren();
            final Iterator<TreeNode> calWeekIterator = cws.iterator();
            while (calWeekIterator.hasNext()) {
                final CalendarWeekTreeNode cwtn = (CalendarWeekTreeNode) calWeekIterator.next();
                final Iterator<TreeNode> trainingChilIterator = cwtn.getChildren().iterator();
                while (trainingChilIterator.hasNext()) {
                    final TrainingChild trainingChild = (TrainingChild) trainingChilIterator.next();
                    if (training.getStartInMillis() == trainingChild.getTraining().getStartInMillis()) {
                        trainingChilIterator.remove();
                        if (cwtn.getChildCount() == 0) {
                            calWeekIterator.remove();
                            if (nextYear.getChildCount() == 0) {
                                yearIterator.remove();
                            }
                        }
                        return;
                    }
                }
            }
        }
    }
}
