package ch.opentrainingcenter.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.TrainingService;
import ch.opentrainingcenter.util.Events.Select;

@ManagedBean(name = "contentViewer")
@ViewScoped
@Named
public class ContentViewer implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentViewer.class);

    private String type;

    @Inject
    private TrainingService service;

    public boolean isTraining() {
        return "training".equals(type);
    }

    public boolean isWeek() {
        return false;
    }

    public boolean isMonth() {
        return false;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void onSelection(@Observes @Select final Training selected) {
        type = "training";
        LOGGER.info("Show Training {}", selected.getId());
        final Training training = service.findFullTraining(selected.getId());
        LOGGER.info("Loaded Training {} with {} trackpoints", training.getId(), training.getTrackPoints().size());
    }

    public void onSelection(@Observes @Select final List<Training> trainings) {
        type = "week";
        LOGGER.info("Show Trainings {}", trainings.size());
    }
}
