package ch.opentrainingcenter.gui.service;

import java.util.function.Function;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.primefaces.model.chart.LineChartModel;

import ch.opentrainingcenter.business.service.ChartService;
import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.model.GTraining.PointData;

@Stateless
public class ChartServiceDelegate {
    @Inject
    private ChartService chartService;

    public LineChartModel createLineChart(final GTraining training, final Function<PointData, Number> functionX, final Function<PointData, Number> functionY,
            final String label, final String color) {
        return chartService.createLineChart(training, functionX, functionY, label, color);
    }

}
