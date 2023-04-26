package com.app.weather.app.model;

import com.google.gson.annotations.SerializedName;

public class Current {

    private long dt;

    private long sunrise;

    private long sunset;

    private double temp;

    @SerializedName("feels_like")
    private double feelsLike;

    private int pressure;

    private int humidity;

    @SerializedName("dew_point")
    private double dewPoint;

    private double uvi;

    private int clouds;

    private int visibility;

    @SerializedName("wind_speed")
    private double windSpeed;

    @SerializedName("wind_deg")
    private int windDeg;

    @SerializedName("wind_gust")
    private double windGust;

    @SerializedName("weather")
    private Weather[] weatherList;

    public Current(long dt, long sunrise, long sunset, double temp, double feelsLike, int pressure, int humidity, double dewPoint, double uvi, int clouds, int visibility, double windSpeed, int windDeg, double windGust, Weather[] weatherList) {
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dewPoint = dewPoint;
        this.uvi = uvi;
        this.clouds = clouds;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.windGust = windGust;
        this.weatherList = weatherList;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public double getUvi() {
        return uvi;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(int windDeg) {
        this.windDeg = windDeg;
    }

    public double getWindGust() {
        return windGust;
    }

    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }

    public Weather[] getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(Weather[] weatherList) {
        this.weatherList = weatherList;
    }
}
