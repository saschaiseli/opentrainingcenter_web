package ch.opentrainingcenter.gui.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.gui.service.DistanceHelper;
import ch.opentrainingcenter.gui.service.TimeHelper;

public class GTraining {

    private final SimpleDateFormat dfDate = new SimpleDateFormat("dd.MM.YYYY", Locale.getDefault());
    private final SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    private final String startDatum;
    private final String startZeit;
    private final String distanz;
    private final String dauer;
    private final String pace;
    private final String avgPace;
    private final String maxPace;
    private final String effect;
    private final long startInMillis;

    public GTraining(final Training training) {
        final Date date = new Date(training.getId());
        startInMillis = date.getTime();
        startDatum = dfDate.format(date);
        startZeit = dfTime.format(date);
        distanz = DistanceHelper.roundDistanceFromMeterToKm(training.getLaengeInMeter());
        dauer = TimeHelper.convertSecondsToHumanReadableZeit(training.getDauer());
        pace = DistanceHelper.calculatePace(training.getLaengeInMeter(), training.getDauer());
        avgPace = String.valueOf(training.getAverageHeartBeat());
        maxPace = String.valueOf(training.getMaxHeartBeat());
        effect = String.valueOf(training.getTrainingEffect().doubleValue() / 10);
    }

    public String getStartDatum() {
        return startDatum;
    }

    public String getStartZeit() {
        return startZeit;
    }

    public String getDistanz() {
        return distanz;
    }

    public String getDauer() {
        return dauer;
    }

    public String getPace() {
        return pace;
    }

    public String getAvgPace() {
        return avgPace;
    }

    public String getMaxPace() {
        return maxPace;
    }

    public String getEffect() {
        return effect;
    }

    public long getStartInMillis() {
        return startInMillis;
    }

}
