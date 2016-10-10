package ch.opentrainingcenter.model.menu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.TreeNode;

import ch.opentrainingcenter.model.Training;

public class MenuTreeNodeTest {

    private final Locale locale = Locale.GERMAN;

    private MenuTreeNode tree;

    @Before
    public void setUp() {
        tree = new MenuTreeNode();
    }

    @Test
    public void testAddTraining() {
        final Training training1 = createTraining(1, 2005, 1, 22);
        tree.addNode(training1);

        final Training training2 = createTraining(2, 2010, 2, 16);
        tree.addNode(training2);

        final Training training3 = createTraining(3, 2001, 3, 11); // KW10
        tree.addNode(training3);

        final Training training4 = createTraining(4, 2001, 3, 12); // KW11
        tree.addNode(training4);

        final Training training5 = createTraining(5, 2001, 3, 13); // KW11
        tree.addNode(training5);

        final List<TreeNode> children = tree.getChildren();
        final YearTreeNode year_2010 = (YearTreeNode) children.get(0);
        final YearTreeNode year_2005 = (YearTreeNode) children.get(1);
        final YearTreeNode year_2001 = (YearTreeNode) children.get(2);

        assertEquals(2010, year_2010.getYear());
        assertEquals(2005, year_2005.getYear());
        assertEquals(2001, year_2001.getYear());
        assertEquals(3, children.size());

        final List<TreeNode> kws_2001 = year_2001.getChildren();
        assertEquals(2, kws_2001.size());

        final CalendarWeekTreeNode cw11 = (CalendarWeekTreeNode) kws_2001.get(0);
        final Training tr11_1 = ((TrainingChild) cw11.getChildren().get(0)).getTraining();
        assertEquals(5, tr11_1.getId());

        final Training tr11_2 = ((TrainingChild) cw11.getChildren().get(1)).getTraining();
        assertEquals(4, tr11_2.getId());
        assertEquals(2, cw11.getChildren().size());

        final CalendarWeekTreeNode cw10 = ((CalendarWeekTreeNode) kws_2001.get(1));
        final Training tr10_1 = ((TrainingChild) cw10.getChildren().get(0)).getTraining();
        assertEquals(3, tr10_1.getId());
        assertEquals(1, cw10.getChildren().size());

        assertEquals(11, cw11.getCalendarWeek());
        assertEquals(10, cw10.getCalendarWeek());
    }

    @Test
    public void testDeleteTraining() {
        // GIVEN
        final Training training1 = createTraining(1, 2005, 1, 22);
        tree.addNode(training1);

        final Training training2 = createTraining(2, 2010, 2, 16);
        tree.addNode(training2);

        final Training training3 = createTraining(3, 2001, 3, 11); // KW10
        tree.addNode(training3);

        final Training training4 = createTraining(4, 2001, 3, 12); // KW11
        tree.addNode(training4);

        final Training training5 = createTraining(5, 2001, 3, 13); // KW11
        tree.addNode(training5);

        // WHEN
        tree.removeNode(training3);

        // THEN
        final List<TreeNode> children = tree.getChildren();
        final YearTreeNode year_2010 = (YearTreeNode) children.get(0);
        final YearTreeNode year_2005 = (YearTreeNode) children.get(1);
        final YearTreeNode year_2001 = (YearTreeNode) children.get(2);

        assertEquals(2010, year_2010.getYear());
        assertEquals(2005, year_2005.getYear());
        assertEquals(2001, year_2001.getYear());
        assertEquals(3, children.size());

        final List<TreeNode> kws_2001 = year_2001.getChildren();
        assertEquals(1, kws_2001.size());

        final CalendarWeekTreeNode cw11 = (CalendarWeekTreeNode) kws_2001.get(0);
        final Training tr11_1 = ((TrainingChild) cw11.getChildren().get(0)).getTraining();
        assertEquals(5, tr11_1.getId());

        final Training tr11_2 = ((TrainingChild) cw11.getChildren().get(1)).getTraining();
        assertEquals(4, tr11_2.getId());
        assertEquals(2, cw11.getChildren().size());

        assertEquals(11, cw11.getCalendarWeek());
    }

    @Test
    public void testDeleteOnlyOneTraining() {
        // GIVEN
        final Training training1 = createTraining(1, 2005, 1, 22);
        tree.addNode(training1);

        // WHEN
        tree.removeNode(training1);

        // THEN
        assertTrue(tree.getChildren().isEmpty());
    }

    @Test
    public void testDeleteTrainingNotFound() {
        // GIVEN
        final Training training1 = createTraining(1, 2005, 1, 22);
        tree.addNode(training1);

        final Training trainingNotAddedToTree = createTraining(2, 2005, 1, 21);

        // WHEN
        tree.removeNode(trainingNotAddedToTree);

        // THEN
        assertFalse(tree.getChildren().isEmpty());
    }

    @Test
    public void testDeleteTrainingSameCalWeek() {
        // GIVEN
        final Training training1 = createTraining(1, 2005, 1, 22);
        tree.addNode(training1);

        final Training training2 = createTraining(2, 2005, 1, 21);
        tree.addNode(training2);
        // WHEN
        tree.removeNode(training2);

        // THEN
        assertEquals(1, tree.getChildren().size());
    }

    private Training createTraining(final long id, final int year, final int month, final int day) {
        final Training training = mock(Training.class);
        final Calendar cal = Calendar.getInstance(locale);
        cal.set(year, month - 1, day);
        when(training.getDateOfStart()).thenReturn(createDate(year, month, day));
        when(training.getId()).thenReturn(id);
        return training;
    }

    private Date createDate(final int year, final int month, final int day) {
        final Calendar cal = Calendar.getInstance(locale);
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
