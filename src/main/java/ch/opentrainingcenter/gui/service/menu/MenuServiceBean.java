package ch.opentrainingcenter.gui.service.menu;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.business.dbaccess.TrainingService;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.gui.controller.MyApplicationScope;
import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.model.menu.MenuTreeNode;

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
        final List<Training> trainings = service.findTrainingByAthlete(appContext.getApplicationUserId());
        for (final Training training : trainings) {
            root.addNode(new GTraining(training));
        }
    }

    public TreeNode getTree() {
        return root;
    }

    public void addTraining(final GTraining training) {
        LOGGER.info("Add Training to Navigation Tree");
        root.addNode(training);
    }

    public void remove(final GTraining training) {
        LOGGER.info("Remove Training from Navigation Tree");
        root.removeNode(training);
    }

}
