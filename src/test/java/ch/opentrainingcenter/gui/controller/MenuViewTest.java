package ch.opentrainingcenter.gui.controller;

import static org.mockito.Mockito.mock;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.primefaces.model.TreeNode;

import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.model.menu.TrainingChild;

@Ignore
public class MenuViewTest {

    @Inject
    private MenuView view;

    @Before
    public void setUp() {
        view = new MenuView();
    }

    @Test
    public void test() {
        Training training = mock(Training.class);
        // Training training = new Training();
        GTraining gTraining = new GTraining(training);
        TrainingChild treeNode = new TrainingChild(gTraining);

        // view.handleNodeSelectEvent(treeNode);

        TreeNode node = view.getSelectedNode();
    }

}
