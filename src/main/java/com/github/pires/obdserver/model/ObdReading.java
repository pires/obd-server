package com.github.pires.obdserver.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name = "OBD_READINGS", indexes = {@Index(columnList = "VIN")})
public class ObdReading implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Version
    private long version;
    private double latitude, longitude;
    private long timestamp;

    @Column(name = "VIN", nullable = false)
    private String vin;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> readings;

    public ObdReading() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Map<String, String> getReadings() {
        return readings;
    }

    public void setReadings(Map<String, String> readings) {
        this.readings = readings;
    }

}
