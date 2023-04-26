package com.app.weather.app.model;

public class GeoDetails {

    private final String country;

    private final String city;

    private Cord cord;

    public GeoDetails(String country, String city, Cord cord) {
        this.country = country;
        this.city = city;
        this.cord = cord;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public Cord getCoord() {
        return cord;
    }

    public void setCoord(Cord cord) {
        this.cord = cord;
    }
}
