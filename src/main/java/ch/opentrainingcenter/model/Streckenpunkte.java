package ch.opentrainingcenter.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Streckenpunkte {

    @Id
    @SequenceGenerator(name = "STRECKENPUNKTE_ID_SEQUENCE", sequenceName = "STRECKENPUNKTE_ID_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRECKENPUNKTE_ID_SEQUENCE")
    private int id;
    private BigDecimal longitude;
    private BigDecimal latitude;

    public Streckenpunkte() {
    }

    public Streckenpunkte(final BigDecimal longitude, final BigDecimal latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(final BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(final BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Override
    @SuppressWarnings("nls")

    public String toString() {
        return "Streckenpunkte [longitude=" + longitude + ", latitude=" + latitude + "]";
    }
}
