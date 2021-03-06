package ch.opentrainingcenter.gui.controller;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.LineChartModel;

import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.model.GTraining.PointData;
import ch.opentrainingcenter.gui.service.ChartServiceDelegate;

@Stateless
@Named
public class SingleChartView extends TrainingSelectionObserver implements Serializable {

    private static final long serialVersionUID = 1L;

    private LineChartModel heartModel;
    private LineChartModel hoeheModel;
    private GTraining training;

    @Inject
    private ChartServiceDelegate chartService;

    public LineChartModel getHeartModel() {
        return heartModel;
    }

    @Override
    void onSelectSingleTraining(final GTraining training) {
        this.training = training;

        heartModel = chartService.createLineChart(training, ((final PointData pd) -> pd.distance), ((final PointData pd) -> pd.heart), "Distanz", "Herz",
                "b30000", 50, 200);
        hoeheModel = chartService.createLineChart(training, ((final PointData pd) -> pd.distance), ((final PointData pd) -> pd.altitude), "Distanz", "Höhe",
                "0099ff");
    }

    public GTraining getTraining() {
        return training;
    }

    public LineChartModel getHoeheModel() {
        return hoeheModel;
    }

}
