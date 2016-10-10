package ch.opentrainingcenter.controller;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.service.GTrainingService;
import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.util.Events.Select;

@ManagedBean(name = "trainingViewer")
@ViewScoped
@Named
public class TrainingViewer implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingViewer.class);

    @Inject
    private GTrainingService service;

    private GTraining training;

    public void onSelection(@Observes @Select final Training selected) {
        LOGGER.info("Show Training {}", selected.getId());
        training = service.loadTraining(selected.getId());
    }

    public GTraining getTraining() {
        return training;
    }

}
