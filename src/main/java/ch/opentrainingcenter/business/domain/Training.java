package ch.opentrainingcenter.business.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({ //
        @NamedQuery(name = "Training.getTrainingByAthlete", query = "SELECT t FROM TRAINING t where t.athlete=:athlete order by t.id desc") //
})
@Cacheable
@Entity(name = "TRAINING")
public class Training {

    @Id
    private long id;
    private long dauer;
    private long laengeInMeter;
    private int averageHeartBeat;
    private int maxHeartBeat;
    private double maxSpeed;

    private String note;

    @ManyToOne
    @JoinColumn(name = "ID_FK_ATHLETE", nullable = false)
    private Athlete athlete;

    @OneToOne
    @JoinColumn(name = "ID_FK_ROUTE")
    private Route route;

    @OneToOne
    @JoinColumn(name = "ID_FK_WEATHER")
    private Weather weather;

    @OneToOne
    @JoinColumn(name = "ID_FK_SHOES")
    private Shoe shoe;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fk_training")
    private List<Tracktrainingproperty> trackPoints = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateOfImport;

    private Integer upMeter;
    private Integer downMeter;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LapInfo> lapInfos = new ArrayList<>();

    @Enumerated(EnumType.ORDINAL)
    private Sport sport;

    @Enumerated(EnumType.ORDINAL)
    private TrainingType trainingType;

    @Column(name = "GEO_QUALITY")
    private Integer fehlerInProzent;

    @Column(name = "TRAINING_EFFECT")
    private Integer trainingEffect;

    public Training() {
    }

    public Training(final RunData runData, final HeartRate heart, final String remark, final Weather wetter, final Route strecke) {
        id = runData.getDateOfStart().getTime();
        dauer = runData.getTimeInSeconds();
        laengeInMeter = runData.getDistanceInMeter();
        averageHeartBeat = heart.getAverage();
        maxHeartBeat = heart.getMax();
        maxSpeed = runData.getMaxSpeed();
        note = remark;
        weather = wetter;
        route = strecke;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(final TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(final Weather weather) {
        this.weather = weather;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(final Shoe shoe) {
        this.shoe = shoe;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(final Athlete athlete) {
        this.athlete = athlete;
    }

    public Date getDateOfStart() {
        return new Date(id);
    }

    public long getDauer() {
        return dauer;
    }

    public void setDauer(final long dauer) {
        this.dauer = dauer;
    }

    public long getLaengeInMeter() {
        return laengeInMeter;
    }

    public void setLaengeInMeter(final long laengeInMeter) {
        this.laengeInMeter = laengeInMeter;
    }

    public int getAverageHeartBeat() {
        return averageHeartBeat;
    }

    public void setAverageHeartBeat(final int averageHeartBeat) {
        this.averageHeartBeat = averageHeartBeat;
    }

    public int getMaxHeartBeat() {
        return maxHeartBeat;
    }

    public void setMaxHeartBeat(final int maxHeartBeat) {
        this.maxHeartBeat = maxHeartBeat;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(final double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(final Route route) {
        this.route = route;
    }

    public List<Tracktrainingproperty> getTrackPoints() {
        return trackPoints;
    }

    public void setTrackPoints(final List<Tracktrainingproperty> trackPoints) {
        this.trackPoints = trackPoints;
    }

    public Date getDateOfImport() {
        return dateOfImport;
    }

    public void setDateOfImport(final Date dateOfImport) {
        this.dateOfImport = dateOfImport;

    }

    public Integer getUpMeter() {
        return upMeter;
    }

    public void setUpMeter(final Integer upMeter) {
        this.upMeter = upMeter;
    }

    public Integer getDownMeter() {
        return downMeter;
    }

    public void setDownMeter(final Integer downMeter) {
        this.downMeter = downMeter;
    }

    public List<LapInfo> getLapInfos() {
        return Collections.unmodifiableList(lapInfos);
    }

    public void setLapInfos(final List<LapInfo> lapInfos) {
        this.lapInfos = lapInfos;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(final Sport sport) {
        this.sport = sport;
    }

    public Integer getGeoQuality() {
        return fehlerInProzent;
    }

    public void setGeoQuality(final Integer fehlerInProzent) {
        this.fehlerInProzent = fehlerInProzent;
    }

    public Integer getTrainingEffect() {
        return trainingEffect;
    }

    public void setTrainingEffect(final Integer trainingEffect) {
        this.trainingEffect = trainingEffect;
    }

    @Override
    public String toString() {
        return "Training [dateOfStart=" + new Date(id) + ", dauer=" + dauer + ", laengeInMeter=" + laengeInMeter + ", averageHeartBeat=" + averageHeartBeat
                + ", maxHeartBeat=" + maxHeartBeat + ", maxSpeed=" + maxSpeed + ", athlete=" + athlete + ", sport=" + sport + ", trainingEffect="
                + trainingEffect + "]";
    }

}
