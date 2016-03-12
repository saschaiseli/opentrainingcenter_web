package ch.opentrainingcenter.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Training {
    @Id
    @SequenceGenerator(name = "TRAINING_ID_SEQUENCE", sequenceName = "TRAINING_ID_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINING_ID_SEQUENCE")
    @Column(name = "ID_TRAINING")
    private int id;

    private long datum;

    private BigDecimal dauer;
    private BigDecimal laengeInMeter;
    private int averageHeartBeat;
    private int maxHeartBeat;
    private BigDecimal maxSpeed;

    private String note;

    @ManyToOne
    @JoinColumn(name = "ID_FK_ATHLETE")
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

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<Tracktrainingproperty> trackPoints = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfImport;

    private String fileName;
    private Integer upMeter;
    private Integer downMeter;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
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

    public Training(final RunData runData, final HeartRate heart, final String note, final Weather weather, final Route route) {
        datum = runData.getDateOfStart();
        dauer = BigDecimal.valueOf(runData.getTimeInSeconds());
        laengeInMeter = BigDecimal.valueOf(runData.getDistanceInMeter());
        averageHeartBeat = heart.getAverage();
        maxHeartBeat = heart.getMax();
        maxSpeed = BigDecimal.valueOf(runData.getMaxSpeed());
        this.note = note;
        this.weather = weather;
        this.route = route;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
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

    public long getDatum() {
        return datum;
    }

    public void setDatum(final long datum) {
        this.datum = datum;
    }

    public double getDauer() {
        return dauer.doubleValue();
    }

    public void setDauer(final double dauer) {
        this.dauer = BigDecimal.valueOf(dauer);
    }

    public double getLaengeInMeter() {
        return laengeInMeter.doubleValue();
    }

    public void setLaengeInMeter(final double laengeinmeter) {
        laengeInMeter = BigDecimal.valueOf(laengeinmeter);
    }

    public int getAverageHeartBeat() {
        return averageHeartBeat;
    }

    public void setAverageHeartBeat(final int averageheartbeat) {
        averageHeartBeat = averageheartbeat;
    }

    public int getMaxHeartBeat() {
        return maxHeartBeat;
    }

    public void setMaxHeartBeat(final int maxheartbeat) {
        maxHeartBeat = maxheartbeat;
    }

    public double getMaxSpeed() {
        return maxSpeed.doubleValue();
    }

    public void setMaxSpeed(final double maxspeed) {
        maxSpeed = BigDecimal.valueOf(maxspeed);
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    @Override
    @SuppressWarnings("nls")

    public String toString() {
        return "Training [datum=" + new Date(datum) + ", dauer=" + dauer + ", laengeInMeter=" + laengeInMeter + ", athlete=" + athlete + ", trainingType="
                + trainingType + ", route=" + route + ", fileName=" + fileName + "]";
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

}
