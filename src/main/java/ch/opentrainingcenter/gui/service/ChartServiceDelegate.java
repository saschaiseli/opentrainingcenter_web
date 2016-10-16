package ch.opentrainingcenter.gui.service;

import java.util.function.Function;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.primefaces.model.chart.LineChartModel;

import ch.opentrainingcenter.business.service.ChartService;
import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.model.GTraining.PointData;
import ch.opentrainingcenter.gui.model.XYChartModel;

@Stateless
public class ChartServiceDelegate {
    @Inject
    private ChartService chartService;

    @Inject
    PFChartCreatorService pfChartService;

    public LineChartModel createLineChart(final GTraining training, final Function<PointData, Number> functionX, final Function<PointData, Number> functionY,
            final String xLabel, final String yLabel, final String color) {
        final XYChartModel dataModel = chartService.createChartDataModel(training, functionX, functionY);
        return pfChartService.createLineChart(dataModel, color, xLabel, yLabel);
    }

    public LineChartModel createLineChart(final GTraining training, final Function<PointData, Number> functionX, final Function<PointData, Number> functionY,
            final String xLabel, final String yLabel, final String color, final int yMin, final int yMax) {
        final XYChartModel dataModel = chartService.createChartDataModel(training, functionX, functionY, yMin, yMax);
        return pfChartService.createLineChart(dataModel, color, xLabel, yLabel);
    }

}
