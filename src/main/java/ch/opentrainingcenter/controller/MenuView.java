package ch.opentrainingcenter.controller;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.menu.MenuServiceBean;
import ch.opentrainingcenter.util.Events.Added;
import ch.opentrainingcenter.util.Events.Deleted;

@ManagedBean
@ViewScoped
public class MenuView implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuView.class);

    private static final long serialVersionUID = 1L;

    @Inject
    private MenuServiceBean service;

    public TreeNode getRoot() {
        return service.getTree();
    }

    public void onAddTraining(@Observes @Added final Training training) {
        LOGGER.info("onAddTraining");
        service.addTraining(training);
        RequestContext.getCurrentInstance().update("treeNavigation:tr");
    }

    public void onDeletedTraining(@Observes @Deleted final Training training) {
        LOGGER.info("onAddTraining");
        service.remove(training);
        RequestContext.getCurrentInstance().update("treeNavigation:tr");
    }

}