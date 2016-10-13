package ch.opentrainingcenter.gui.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ch.opentrainingcenter.business.domain.Tracktrainingproperty;
import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.gui.service.DistanceHelper;
import ch.opentrainingcenter.gui.service.TimeHelper;

public class GTraining {

    public class Coord {
        public final double lat;
        public final double lng;

        public Coord(final double lat, final double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        public String toString() {
            return "lat: " + lat + " lng: " + lng;
        }
    }

    public class PointData {

        public final double distance;
        public final int altitude;
        public final int heart;
        public final long zeitInMillis;

        public PointData(final double distance, final int altitude, final int heart, final long zeitInMillis) {
            this.distance = distance;
            this.altitude = altitude;
            this.heart = heart;
            this.zeitInMillis = zeitInMillis;
        }
    }

    private final SimpleDateFormat dfDate = new SimpleDateFormat("dd.MM.YYYY", Locale.getDefault());
    private final SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    private final String startDatum;
    private final String startZeit;
    private final String distanz;
    private final String dauer;
    private final String pace;
    private final String avgHeart;
    private final String maxHeart;
    private final String effect;
    private final long startInMillis;

    private final List<Coord> coords = new ArrayList<>();
    private final List<PointData> datas = new ArrayList<>();

    public GTraining(final Training training) {
        this(training, Collections.emptyList());
    }

    public GTraining(final Training training, final List<Tracktrainingproperty> trackpoints) {
        final Date date = new Date(training.getId());
        startInMillis = date.getTime();
        startDatum = dfDate.format(date);
        startZeit = dfTime.format(date);
        distanz = DistanceHelper.roundDistanceFromMeterToKm(training.getLaengeInMeter());
        dauer = TimeHelper.convertSecondsToHumanReadableZeit(training.getDauer());
        pace = DistanceHelper.calculatePace(training.getLaengeInMeter(), training.getDauer());
        avgHeart = String.valueOf(training.getAverageHeartBeat());
        maxHeart = String.valueOf(training.getMaxHeartBeat());
        effect = String.valueOf(training.getTrainingEffect().doubleValue() / 10);
        for (final Tracktrainingproperty point : trackpoints) {
            if (point.getLatitude() != null && point.getLongitude() != null) {
                final Coord cord = new Coord(point.getLatitude(), point.getLongitude());
                coords.add(cord);
            }
            datas.add(new PointData(point.getDistance(), point.getAltitude(), point.getHeartBeat(), point.getZeit()));
        }
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

    public String getAvgHeart() {
        return avgHeart;
    }

    public String getMaxHeart() {
        return maxHeart;
    }

    public String getEffect() {
        return effect;
    }

    public long getStartInMillis() {
        return startInMillis;
    }

    public List<Coord> getCoords() {
        return Collections.unmodifiableList(coords);
    }

    public List<PointData> getDatas() {
        return Collections.unmodifiableList(datas);
    }

}
