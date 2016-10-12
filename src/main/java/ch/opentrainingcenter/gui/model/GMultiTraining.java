package ch.opentrainingcenter.gui.model;

import java.util.List;

public class GMultiTraining {

    private final List<GTraining> trainings;
    private final String distance;
    private final String dauer;
    private final String avgHeart;
    private final String maxHeart;
    private final String pace;

    public GMultiTraining(final List<GTraining> ts, final String dauer, final String distance, final String pace, final String avgHeart,
            final String maxHeart) {
        this.trainings = ts;
        this.dauer = dauer;
        this.distance = distance;
        this.pace = pace;
        this.avgHeart = avgHeart;
        this.maxHeart = maxHeart;
    }

    public List<GTraining> getTrainings() {
        return trainings;
    }

    public String getDistance() {
        return distance;
    }

    public String getDauer() {
        return dauer;
    }

    public String getAvgHeart() {
        return avgHeart;
    }

    public String getMaxHeart() {
        return maxHeart;
    }

    public String getPace() {
        return pace;
    }

}
