package ch.opentrainingcenter.model;

/**
 * Info zu einem Lauf
 */
public class RunData {

    private final long dateOfStart;
    private final long timeInSeconds;
    private final long distanceInMeter;
    private final double maxSpeed;

    /**
     * @param dateOfStart
     *            Zeitpunkt des Startes in ms
     * @param timeInSeconds
     *            Dauer des Laufes in Sekunden
     * @param distanceInMeter
     *            Distanz in meter
     * @param maxSpeed
     */
    public RunData(final long dateOfStart, final long timeInSeconds, final long distanceInMeter, final double maxSpeed) {
        this.dateOfStart = dateOfStart;
        this.timeInSeconds = timeInSeconds;
        this.distanceInMeter = distanceInMeter;
        this.maxSpeed = maxSpeed;
    }

    /**
     * @return Zeitpunkt des Startes in ms
     */
    public long getDateOfStart() {
        return dateOfStart;
    }

    /**
     * @return Dauer des Laufes in Sekunden
     */
    public long getTimeInSeconds() {
        return timeInSeconds;
    }

    /**
     * @return Distanz in meter
     */
    public long getDistanceInMeter() {
        return distanceInMeter;
    }

    /**
     * @return maximale geschwindigkeit
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }
}
