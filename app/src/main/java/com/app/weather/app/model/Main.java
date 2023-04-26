package com.app.weather.app.model;

public class Main {

    private double temp;

    private int pressure;

    private double wind;

    private int humidity;

    public Main(double temp, int pressure, double wind, int humidity) {
        this.temp = temp;
        this.pressure = pressure;
        this.wind = wind;
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
