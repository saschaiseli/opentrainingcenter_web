package ch.opentrainingcenter.gui.controller;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.event.Observes;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ch.opentrainingcenter.business.service.GTrainingService;
import ch.opentrainingcenter.gui.controller.Events.Select;
import ch.opentrainingcenter.gui.model.GMultiTraining;
import ch.opentrainingcenter.gui.model.GTraining;

@ManagedBean(name = "multiTrainingView")
@ViewScoped
@Named
public class MultiTrainingView implements Serializable {

    private static final long serialVersionUID = 2L;

    @Inject
    private GTrainingService service;

    private GMultiTraining model;

    public void onSelection(@Observes @Select final List<GTraining> selected) {
        final List<Long> ids = selected.stream().map(gt -> gt.getStartInMillis()).collect(Collectors.toList());
        model = service.loadMultiTrainings(ids);
    }

    public GMultiTraining getModel() {
        return model;
    }

}
