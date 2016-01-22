package ch.opentrainingcenter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Route {

    @Id
    @SequenceGenerator(name = "ROUTE_ID_SEQUENCE", sequenceName = "ROUTE_ID_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROUTE_ID_SEQUENCE")
    private int id;
    private String name;
    private String beschreibung;

    @ManyToOne
    @JoinColumn(name = "ID_FK_ATHLETE")
    private Athlete athlete;

    @OneToOne
    @JoinColumn(name = "ID_FK_TRAINING")
    private Training referenzTrack;

    public Route() {
    }

    public Route(final String name, final Athlete athlete) {
        this.name = name;
        this.athlete = athlete;
    }

    public Route(final String name, final String beschreibung, final Training referenzTraining) {
        this.name = name;
        this.beschreibung = beschreibung;
        athlete = referenzTraining.getAthlete();
        referenzTrack = referenzTraining;
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

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(final String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setAthlete(final Athlete athlete) {
        this.athlete = athlete;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public Training getReferenzTrack() {
        return referenzTrack;
    }

    public void setReferenzTrack(final Training referenzTrack) {
        this.referenzTrack = referenzTrack;
    }

    @Override
    @SuppressWarnings("nls")

    public String toString() {
        return "Route [name=" + name + ", beschreibung=" + beschreibung + ", athlete=" + athlete + "]";
    }

}
