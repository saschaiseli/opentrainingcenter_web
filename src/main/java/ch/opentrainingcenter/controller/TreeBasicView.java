package ch.opentrainingcenter.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.util.Events.Added;

@ManagedBean(name = "treeBasicView")
@ViewScoped
public class TreeBasicView implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TreeBasicView.class);

    private static final long serialVersionUID = 1L;
    private TreeNode root;

    private List<Training> trainings;

    @PostConstruct
    public void init() {
        root = new DefaultTreeNode("Root", null);
        final TreeNode node0 = new DefaultTreeNode("Node 0", root);
        final TreeNode node1 = new DefaultTreeNode("Node 1", root);

        final TreeNode node00 = new DefaultTreeNode("Node 0.0", node0);
        final TreeNode node01 = new DefaultTreeNode("Node 0.1", node0);

        final TreeNode node10 = new DefaultTreeNode("Node 1.0", node1);

        node1.getChildren().add(new DefaultTreeNode("Node 1.1"));
        node00.getChildren().add(new DefaultTreeNode("Node 0.0.0"));
        node00.getChildren().add(new DefaultTreeNode("Node 0.0.1"));
        node01.getChildren().add(new DefaultTreeNode("Node 0.1.0"));
        node10.getChildren().add(new DefaultTreeNode("Node 1.0.0"));
        root.getChildren().add(new DefaultTreeNode("Node 2"));
    }

    public TreeNode getRoot() {
        return root;
    }

    public void onAddTraining(@Observes @Added final Training training) {
        LOGGER.info("onAddTraining to tree: {}", training);
        trainings.add(training);
        // training.get
        final List<TreeNode> children = root.getChildren();
        // children.contains(o)
    }
}