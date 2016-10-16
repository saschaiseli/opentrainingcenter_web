package ch.opentrainingcenter.gui.service;

import javax.ejb.Stateless;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import ch.opentrainingcenter.gui.model.XYChartModel;
import ch.opentrainingcenter.gui.model.XYData;

@Stateless
public class PFChartCreatorService {

    public LineChartModel createLineChart(final XYChartModel dataModel, final String color, final String xLabel, final String yLabel) {
        final LineChartModel model = new LineChartModel();
        model.setZoom(true);
        model.setSeriesColors(color);
        model.setExtender("chartExtender");

        final LineChartSeries serie = new LineChartSeries();
        serie.setYaxis(AxisType.Y);
        serie.setFill(true);
        serie.setSmoothLine(true);
        serie.setFillAlpha(0.7d);

        dataModel.getXyValues().stream().forEach((final XYData xy) -> serie.set(xy.x, xy.y));

        final Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setMin(dataModel.getxMin());
        xAxis.setMax(dataModel.getxMax());
        xAxis.setLabel(xLabel);

        final Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel(yLabel);
        yAxis.setMin(dataModel.getyMin().doubleValue() * 0.97);
        yAxis.setMax(dataModel.getyMax().doubleValue() * 1.03);

        model.addSeries(serie);
        return model;
    }

}
