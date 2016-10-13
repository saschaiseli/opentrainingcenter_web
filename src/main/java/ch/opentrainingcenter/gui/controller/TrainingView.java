package ch.opentrainingcenter.gui.controller;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.business.service.GTrainingService;
import ch.opentrainingcenter.gui.controller.Events.Select;
import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.model.GTraining.Coord;

@ManagedBean
@ViewScoped
@Named
public class TrainingView implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingView.class);

    @Inject
    private GTrainingService service;

    private GTraining training;

    private MapModel polylineModel;

    public void onSelection(@Observes @Select final GTraining selected) {
        LOGGER.info("Show Training {}", selected.getStartInMillis());
        training = service.loadTraining(selected.getStartInMillis());
        final Polyline polyline = new Polyline();
        for (final Coord coord : training.getCoords()) {
            polyline.getPaths().add(new LatLng(coord.lat, coord.lng));
        }
        polyline.setStrokeWeight(3);
        polyline.setStrokeColor("#FF9900");
        polyline.setStrokeOpacity(1);
        polylineModel = new DefaultMapModel();
        polylineModel.addOverlay(polyline);
    }

    public GTraining getTraining() {
        return training;
    }

    public MapModel getPolylineModel() {
        return polylineModel;
    }

    public void onPolylineSelect(final OverlaySelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Polyline Selected", null));
    }
}
