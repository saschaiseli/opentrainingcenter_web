package ch.opentrainingcenter.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Athlete {

    @Id
    @SequenceGenerator(name = "ATHLETE_ID_SEQUENCE", sequenceName = "ATHLETE_ID_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATHLETE_ID_SEQUENCE")
    private int id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private Integer maxheartrate;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.REMOVE)
    private Set<Route> routes = new HashSet<>();

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.REMOVE)
    private Set<Health> healths = new HashSet<>();

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.REMOVE)
    private Set<Training> trainings = new HashSet<>();

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.REMOVE)
    private Set<Planungwoche> planungwoches = new HashSet<>();

    public Athlete() {
    }

    public Athlete(final String name, final Date birthday, final Integer maxheartrate) {
        this.name = name;
        this.birthday = birthday;
        this.maxheartrate = maxheartrate;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public Integer getMaxheartrate() {
        return maxheartrate;
    }

    public void setMaxheartrate(final Integer maxheartrate) {
        this.maxheartrate = maxheartrate;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(final Set<Route> routes) {
        this.routes = routes;
    }

    public Set<Planungwoche> getPlanungwoches() {
        return planungwoches;
    }

    public void setPlanungwoches(final Set<Planungwoche> planungwoches) {
        this.planungwoches = planungwoches;
    }

    public void addTraining(final Training record) {
        trainings.add(record);
    }

    public Integer getMaxHeartRate() {
        return maxheartrate;
    }

    public void setMaxHeartRate(final Integer maxHeartRate) {
        maxheartrate = maxHeartRate;

    }

    public Set<Health> getHealths() {
        return healths;
    }

    public void setHealths(final Set<Health> healths) {
        this.healths = healths;
    }

    public Set<Training> getTrainings() {
        return Collections.emptySet();
    }

    public void setTrainings(final Set<Training> trainings) {
        this.trainings = trainings;

    }

    @Override
    @SuppressWarnings("nls")

    public String toString() {
        return "Athlete [id=" + id + ", name=" + name + ", birthday=" + birthday + ", maxheartrate=" + maxheartrate + "]";
    }
}
