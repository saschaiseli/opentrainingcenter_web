package ch.opentrainingcenter.business.service;

import java.util.List;
import java.util.function.Function;

import javax.ejb.Stateless;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.model.GTraining.PointData;

@Stateless
public class ChartService {

    public LineChartModel createLineChart(final GTraining training, final Function<PointData, Number> functionX, final Function<PointData, Number> functionY,
            final String label, final String color) {

        final LineChartModel model = new LineChartModel();
        model.setZoom(true);
        model.setSeriesColors(color);
        model.setExtender("chartExtender");

        final LineChartSeries serie = new LineChartSeries();
        serie.setYaxis(AxisType.Y);
        serie.setFill(true);
        serie.setSmoothLine(true);
        serie.setFillAlpha(0.7d);

        final List<PointData> datas = training.getDatas();
        double dist = 0;
        Number min = Double.MAX_VALUE;
        Number max = Double.MIN_VALUE;
        for (final PointData xy : datas) {
            final Number xValue = functionX.apply(xy);
            final Number yValue = functionY.apply(xy);
            System.out.println(xValue + " " + yValue);
            serie.set(xValue, yValue);
            if (min.doubleValue() > yValue.doubleValue()) {
                min = yValue;
            }
            if (max.doubleValue() < yValue.doubleValue()) {
                max = yValue;
            }
            dist = xValue.doubleValue();
        }

        final Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setMin(0);
        xAxis.setMax(dist);

        model.addSeries(serie);

        final Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel(label);
        yAxis.setMin(min.doubleValue() * 0.97);
        yAxis.setMax(max.doubleValue() * 1.03);

        return model;
    }

    public LineChartModel createLineChart(final GTraining training, final Function<PointData, Number> functionX, final Function<PointData, Number> functionY,
            final String label, final String color, final int yMin, final int yMax) {
        final LineChartModel model = createLineChart(training, functionX, functionY, label, color);
        final Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setMin(yMin);
        yAxis.setMax(yMax);
        return model;
    }
}
