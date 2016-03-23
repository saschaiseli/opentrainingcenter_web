package ch.opentrainingcenter.service.menu;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.controller.MyApplicationScope;
import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.model.menu.MenuTreeNode;
import ch.opentrainingcenter.service.TrainingService;

@SessionScoped
public class MenuServiceBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceBean.class);

    private MenuTreeNode root;

    @Inject
    private TrainingService service;

    @Inject
    private MyApplicationScope appContext;

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("Post Construct");
        root = new MenuTreeNode();
        final List<Training> trainings = service.findTrainingByAthlete(appContext.getApplicationUser());
        for (final Training training : trainings) {
            root.addNode(training);
        }
    }

    public TreeNode getTree() {
        return root;
    }

    public void addTraining(final Training training) {
        LOGGER.info("Add Training to Navigation Tree");
        root.addNode(training);
    }

}
