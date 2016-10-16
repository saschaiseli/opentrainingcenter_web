package ch.opentrainingcenter.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.ejb.Stateless;

import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.model.GTraining.PointData;
import ch.opentrainingcenter.gui.model.XYChartModel;
import ch.opentrainingcenter.gui.model.XYData;

@Stateless
public class ChartService {

    public XYChartModel createChartDataModel(final GTraining training, final Function<PointData, Number> functionX,
            final Function<PointData, Number> functionY) {

        final List<XYData> xyValues = new ArrayList<>();
        final List<PointData> datas = training.getDatas();
        double dist = 0;
        Number min = Double.MAX_VALUE;
        Number max = Double.MIN_VALUE;
        for (final PointData xy : datas) {
            final Number xValue = functionX.apply(xy);
            final Number yValue = functionY.apply(xy);
            xyValues.add(new XYData(xValue, yValue));
            if (min.doubleValue() > yValue.doubleValue()) {
                min = yValue;
            }
            if (max.doubleValue() < yValue.doubleValue()) {
                max = yValue;
            }
            dist = xValue.doubleValue();
        }

        return new XYChartModel(xyValues, 0, dist, min.doubleValue() * 0.97, max.doubleValue() * 1.03);
    }

    public XYChartModel createChartDataModel(final GTraining training, final Function<PointData, Number> functionX, final Function<PointData, Number> functionY,
            final int yMin, final int yMax) {
        final XYChartModel model = createChartDataModel(training, functionX, functionY);
        model.setYMin(yMin);
        model.setYMax(yMax);
        return model;
    }
}
