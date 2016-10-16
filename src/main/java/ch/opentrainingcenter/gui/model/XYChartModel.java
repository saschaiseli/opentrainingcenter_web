package ch.opentrainingcenter.gui.model;

import java.util.Collections;
import java.util.List;

public class XYChartModel {

    private final Number xMin;

    private final Number xMax;

    private Number yMin;

    private Number yMax;

    private final List<XYData> xyValues;

    public XYChartModel(final List<XYData> xyValues, final Number xMin, final Number xMax, final Number yMin, final Number yMax) {
        this.xyValues = xyValues;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public List<XYData> getXyValues() {
        return Collections.unmodifiableList(xyValues);
    }

    public Number getxMin() {
        return xMin;
    }

    public Number getxMax() {
        return xMax;
    }

    public Number getyMin() {
        return yMin;
    }

    public Number getyMax() {
        return yMax;
    }

    public void setYMin(final Number yMin) {
        this.yMin = yMin;
    }

    public void setYMax(final Number yMax) {
        this.yMax = yMax;
    }

}
