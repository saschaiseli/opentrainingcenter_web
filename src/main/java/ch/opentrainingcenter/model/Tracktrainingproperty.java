package ch.opentrainingcenter.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Tracktrainingproperty {

    @Id
    @SequenceGenerator(name = "TRACKPOINTPROPERTY_ID_SEQUENCE", sequenceName = "TRACKPOINTPROPERTY_ID_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRACKPOINTPROPERTY_ID_SEQUENCE")
    private int id;
    private BigDecimal distance;
    private int heartbeat;
    private int altitude;
    private long zeit;
    private Double longitude;
    private Double latitude;

    @ManyToOne
    @JoinColumn(name = "ID_TRAINING")
    private Training training;

    private int lap;

    public Tracktrainingproperty() {
    }

    public Tracktrainingproperty(final BigDecimal distance, final int heartbeat, final int altitude, final long zeit, final int lap, final Double longitude,
            final Double latitude) {
        this.distance = distance;
        this.heartbeat = heartbeat;
        this.altitude = altitude;
        this.zeit = zeit;
        this.lap = lap;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(final BigDecimal distance) {
        this.distance = distance;
    }

    public int getHeartBeat() {
        return heartbeat;
    }

    public void setHeartBeat(final int heartbeat) {
        this.heartbeat = heartbeat;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(final int altitude) {
        this.altitude = altitude;
    }

    public long getZeit() {
        return zeit;
    }

    public void setZeit(final long zeit) {
        this.zeit = zeit;
    }

    public int getLap() {
        return lap;
    }

    public void setLap(final int lap) {
        this.lap = lap;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(final Training training) {
        this.training = training;
    }

    @Override
    public String toString() {
        return "Tracktrainingproperty [id=" + id + ", distance=" + distance + ", heartbeat=" + heartbeat + ", altitude=" + altitude + ", zeit=" + zeit
                + ", longitude=" + longitude + ", latitude=" + latitude + ", training=" + training + ", lap=" + lap + "]";
    }

}
