package ch.opentrainingcenter.gui.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.model.GTraining.Coord;

@ManagedBean
@ViewScoped
@Named
public class TrainingView extends TrainingSelectionObserver implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingView.class);

    private GTraining training;

    private MapModel polylineModel;

    @Override
    void onSelectSingleTraining(final GTraining training) {
        this.training = training;
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

    public MapModel getPolylineModel() {
        return polylineModel;
    }

    public void onPolylineSelect(final OverlaySelectEvent event) {
        LOGGER.info("Line Selected: " + event);
    }

    public GTraining getTraining() {
        return training;
    }

}
