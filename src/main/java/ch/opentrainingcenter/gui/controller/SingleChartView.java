package ch.opentrainingcenter.gui.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.model.GTraining.PointData;

@Stateless
@Named
public class SingleChartView extends TrainingSelectionObserver implements Serializable {

    private static final long serialVersionUID = 1L;

    private LineChartModel heartModel;

    private GTraining training;

    public LineChartModel getHeartModel() {
        return heartModel;
    }

    @Override
    void onSelectSingleTraining(final GTraining training) {
        this.training = training;
        heartModel = new LineChartModel();
        heartModel.setZoom(true);
        heartModel.setSeriesColors("F56767, B5F567");

        final LineChartSeries series1 = new LineChartSeries();
        series1.setYaxis(AxisType.Y);
        series1.setFill(true);
        series1.setSmoothLine(true);
        series1.setFillAlpha(0.7d);

        final LineChartSeries series2 = new LineChartSeries();
        series2.setYaxis(AxisType.Y2);

        final List<PointData> datas = training.getDatas();
        double dist = 0;
        double hoeheMax = Double.MIN_VALUE;
        double hoeheMin = Double.MAX_VALUE;
        for (final PointData xy : datas) {
            series1.set(xy.distance, xy.heart);
            series2.set(xy.distance, xy.altitude);
            System.out.println(xy.distance + " " + xy.altitude);
            dist = xy.distance;
            if (hoeheMax < xy.altitude) {
                hoeheMax = xy.altitude;
            }
            if (hoeheMin > xy.altitude) {
                hoeheMin = xy.altitude;
            }
        }

        final Axis xAxis = heartModel.getAxis(AxisType.X);
        xAxis.setMin(-100);
        xAxis.setMax(dist * 1.03);

        heartModel.addSeries(series1);
        heartModel.addSeries(series2);

        final Axis yAxis = heartModel.getAxis(AxisType.Y);
        yAxis.setLabel("Herzfrequenz");
        yAxis.setMin(40);
        yAxis.setMax(230);

        final Axis y2Axis = new LinearAxis("Höhe");
        y2Axis.setMin(hoeheMin * 0.97);
        y2Axis.setMax(hoeheMax * 1.03);

        heartModel.getAxes().put(AxisType.Y2, y2Axis);

    }

    public GTraining getTraining() {
        return training;
    }
}
