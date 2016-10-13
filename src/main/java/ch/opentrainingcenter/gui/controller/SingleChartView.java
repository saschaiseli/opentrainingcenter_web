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
        final LineChartSeries series1 = new LineChartSeries();
        series1.setYaxis(AxisType.Y);
        series1.setFill(true);
        series1.setSmoothLine(true);
        series1.setFillAlpha(0.7d);
        // series1.setLabel("Herzfrequenz");
        final LineChartSeries series2 = new LineChartSeries();
        series2.setYaxis(AxisType.Y2);
        // series2.setLabel("Höhe");

        final List<PointData> datas = training.getDatas();
        for (final PointData xy : datas) {
            series1.set(xy.distance, xy.heart);
            series2.set(xy.distance, xy.altitude);
            System.out.println(xy.distance + " " + xy.altitude);
        }

        // heartModel.getAxes().put(AxisType.X, new
        // CategoryAxis("Herzfrequenz"));
        // heartModel.getAxes().put(AxisType.X2, new CategoryAxis("Höhe"));
        heartModel.addSeries(series1);
        heartModel.addSeries(series2);

        final Axis yAxis = heartModel.getAxis(AxisType.Y);
        yAxis.setLabel("Herzfrequenz");
        yAxis.setMin(40);
        yAxis.setMax(230);

        final Axis y2Axis = new LinearAxis("Höhe");
        y2Axis.setMin(500);
        y2Axis.setMax(700);

        heartModel.getAxes().put(AxisType.Y2, y2Axis);

    }

    public GTraining getTraining() {
        return training;
    }
}
