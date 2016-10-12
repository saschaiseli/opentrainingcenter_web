package ch.opentrainingcenter.gui.model.menu;

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

import ch.opentrainingcenter.gui.model.GTraining;

public class MenuTreeNodeTest {

    private final Locale locale = Locale.GERMAN;

    private MenuTreeNode tree;

    @Before
    public void setUp() {
        tree = new MenuTreeNode("blabla");
    }

    @Test
    public void testAddGTraining() {
        final GTraining GTraining1 = createGTraining(2005, 1, 22);
        tree.addNode(GTraining1);

        final GTraining GTraining2 = createGTraining(2010, 2, 16);
        tree.addNode(GTraining2);

        final long id3 = createDate(2001, 3, 11).getTime();
        final GTraining GTraining3 = createGTraining(2001, 3, 11); // KW10
        tree.addNode(GTraining3);

        final long id4 = createDate(2001, 3, 12).getTime();
        final GTraining GTraining4 = createGTraining(2001, 3, 12); // KW11
        tree.addNode(GTraining4);

        final long id5 = createDate(2001, 3, 13).getTime();
        final GTraining GTraining5 = createGTraining(2001, 3, 13); // KW11
        tree.addNode(GTraining5);

        final List<TreeNode> children = tree.getChildren();
        final YearTreeNode year_2010 = (YearTreeNode) children.get(0);
        final YearTreeNode year_2005 = (YearTreeNode) children.get(1);
        final YearTreeNode year_2001 = (YearTreeNode) children.get(2);

        assertEquals(2010, year_2010.getYear());
        assertEquals(2005, year_2005.getYear());
        assertEquals(2001, year_2001.getYear());
        assertEquals(3, children.size());

        final List<TreeNode> months_2001 = year_2001.getChildren();
        assertEquals(1, months_2001.size());

        final List<TreeNode> kws = months_2001.get(0).getChildren();

        final CalendarWeekTreeNode cw11 = (CalendarWeekTreeNode) kws.get(0);
        final GTraining tr11_1 = ((TrainingChild) cw11.getChildren().get(0)).getTraining();
        assertEquals(id5, tr11_1.getStartInMillis());

        final GTraining tr11_2 = ((TrainingChild) cw11.getChildren().get(1)).getTraining();
        assertEquals(id4, tr11_2.getStartInMillis());
        assertEquals(2, cw11.getChildren().size());

        final CalendarWeekTreeNode cw10 = ((CalendarWeekTreeNode) kws.get(1));
        final GTraining tr10_1 = ((TrainingChild) cw10.getChildren().get(0)).getTraining();
        assertEquals(id3, tr10_1.getStartInMillis());
        assertEquals(1, cw10.getChildren().size());

        assertEquals(11, cw11.getCalendarWeek());
        assertEquals(10, cw10.getCalendarWeek());
    }

    @Test
    public void testDeleteGTraining() {
        // GIVEN
        final GTraining GTraining1 = createGTraining(2005, 1, 22);
        tree.addNode(GTraining1);

        final GTraining GTraining2 = createGTraining(2010, 2, 16);
        tree.addNode(GTraining2);

        final GTraining GTraining3 = createGTraining(2001, 3, 11); // KW10
        tree.addNode(GTraining3);

        final long id4 = createDate(2001, 3, 12).getTime();
        final GTraining GTraining4 = createGTraining(2001, 3, 12); // KW11
        tree.addNode(GTraining4);

        final long id5 = createDate(2001, 3, 13).getTime();
        final GTraining GTraining5 = createGTraining(2001, 3, 13); // KW11
        tree.addNode(GTraining5);

        // WHEN
        tree.removeNode(GTraining3);

        // THEN
        final List<TreeNode> children = tree.getChildren();
        final YearTreeNode year_2010 = (YearTreeNode) children.get(0);
        final YearTreeNode year_2005 = (YearTreeNode) children.get(1);
        final YearTreeNode year_2001 = (YearTreeNode) children.get(2);

        assertEquals(2010, year_2010.getYear());
        assertEquals(2005, year_2005.getYear());
        assertEquals(2001, year_2001.getYear());
        assertEquals(3, children.size());

        final List<TreeNode> months_2001 = year_2001.getChildren();
        assertEquals(1, months_2001.size());

        final CalendarWeekTreeNode cw11 = (CalendarWeekTreeNode) months_2001.get(0).getChildren().get(0);
        final GTraining tr11_1 = ((TrainingChild) cw11.getChildren().get(0)).getTraining();
        assertEquals(id5, tr11_1.getStartInMillis());

        final GTraining tr11_2 = ((TrainingChild) cw11.getChildren().get(1)).getTraining();
        assertEquals(id4, tr11_2.getStartInMillis());
        assertEquals(2, cw11.getChildren().size());

        assertEquals(11, cw11.getCalendarWeek());
    }

    @Test
    public void testDeleteOnlyOneGTraining() {
        // GIVEN
        final GTraining GTraining1 = createGTraining(2005, 1, 22);
        tree.addNode(GTraining1);

        // WHEN
        tree.removeNode(GTraining1);

        // THEN
        assertTrue(tree.getChildren().isEmpty());
    }

    @Test
    public void testDeleteGTrainingNotFound() {
        // GIVEN
        final GTraining GTraining1 = createGTraining(2005, 1, 22);
        tree.addNode(GTraining1);

        final GTraining GTrainingNotAddedToTree = createGTraining(2005, 1, 21);

        // WHEN
        tree.removeNode(GTrainingNotAddedToTree);

        // THEN
        assertFalse(tree.getChildren().isEmpty());
    }

    @Test
    public void testDeleteGTrainingSameCalWeek() {
        // GIVEN
        final GTraining GTraining1 = createGTraining(2005, 1, 22);
        tree.addNode(GTraining1);

        final GTraining GTraining2 = createGTraining(2005, 1, 21);
        tree.addNode(GTraining2);
        // WHEN
        tree.removeNode(GTraining2);

        // THEN
        assertEquals(1, tree.getChildren().size());
    }

    private GTraining createGTraining(final int year, final int month, final int day) {
        final GTraining gTraining = mock(GTraining.class);
        final Calendar cal = Calendar.getInstance(locale);
        cal.set(year, month - 1, day);
        when(gTraining.getStartInMillis()).thenReturn(createDate(year, month, day).getTime());
        return gTraining;
    }

    private Date createDate(final int year, final int month, final int day) {
        final Calendar cal = Calendar.getInstance(locale);
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
