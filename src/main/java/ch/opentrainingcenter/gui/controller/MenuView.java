package ch.opentrainingcenter.gui.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.gui.controller.Events.Added;
import ch.opentrainingcenter.gui.controller.Events.Deleted;
import ch.opentrainingcenter.gui.controller.Events.Select;
import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.model.menu.CalendarWeekTreeNode;
import ch.opentrainingcenter.gui.model.menu.TrainingChild;
import ch.opentrainingcenter.gui.model.menu.YearTreeNode;
import ch.opentrainingcenter.gui.service.menu.MenuServiceBean;

@ManagedBean
@ViewScoped
public class MenuView implements Serializable {

    private enum ContentType {
        TRAINING, WEEK, YEAR
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuView.class);

    private static final long serialVersionUID = 1L;

    private ContentType type;

    @Inject
    @Select
    private Event<GTraining> selectionEvent;

    @Inject
    @Select
    private Event<List<GTraining>> weekEvent;

    private TreeNode selectedNode;

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(final TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    @Inject
    private MenuServiceBean service;

    public TreeNode getRoot() {
        return service.getTree();
    }

    public void onAddTraining(@Observes @Added final GTraining training) {
        LOGGER.info("onAddTraining");
        service.addTraining(training);
        RequestContext.getCurrentInstance().update("treeNavigation:tr");
    }

    public void onDeletedTraining(@Observes @Deleted final GTraining training) {
        LOGGER.info("onAddTraining");
        service.remove(training);
        RequestContext.getCurrentInstance().update("treeNavigation:tr");
    }

    public void onNodeSelect(final NodeSelectEvent event) {
        final Object source = event.getTreeNode();
        if (source instanceof TrainingChild) {
            final TrainingChild tc = (TrainingChild) source;
            selectionEvent.fire(tc.getTraining());
            type = ContentType.TRAINING;
        } else if (source instanceof CalendarWeekTreeNode) {
            final CalendarWeekTreeNode week = (CalendarWeekTreeNode) source;
            final List<TreeNode> children = week.getChildren();
            final List<GTraining> trainings = new ArrayList<>();
            for (final TreeNode treeNode : children) {
                final TrainingChild tc = (TrainingChild) treeNode;
                trainings.add(tc.getTraining());
            }
            weekEvent.fire(trainings);
            type = ContentType.WEEK;
        } else if (source instanceof YearTreeNode) {
            type = ContentType.YEAR;
        }
        RequestContext.getCurrentInstance().update("main_content");
    }

    public boolean isTraining() {
        return ContentType.TRAINING.equals(type);
    }

    public boolean isWeek() {
        return ContentType.WEEK.equals(type);
    }

    public boolean isYear() {
        return ContentType.YEAR.equals(type);
    }
}