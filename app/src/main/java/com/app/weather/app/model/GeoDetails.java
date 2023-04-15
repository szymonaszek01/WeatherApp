package com.app.weather.app.model;

public class GeoDetails {

    private final String country;

    private final String city;

    private Coord coord;

    public GeoDetails(String country, String city, Coord coord) {
        this.country = country;
        this.city = city;
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
