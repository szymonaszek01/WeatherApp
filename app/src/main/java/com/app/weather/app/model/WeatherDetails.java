package com.app.weather.app.model;

import java.util.List;

public class WeatherDetails {

    private List<Weather> weatherList;

    private Main main;

    private Wind wind;

    private Rain rain;

    private Clouds clouds;

    public WeatherDetails(List<Weather> weatherList, Main main, Wind wind, Rain rain, Clouds clouds) {
        this.weatherList = weatherList;
        this.main = main;
        this.wind = wind;
        this.rain = rain;
        this.clouds = clouds;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }
}
