package com.app.weather.app.model;

public class WeatherDetailsBase {

    private Main main;

    private String icon;

    private long dt;

    private String unitSystem;

    public WeatherDetailsBase(Main main, String icon, long dt, String unitSystem) {
        this.main = main;
        this.icon = icon;
        this.dt = dt;
        this.unitSystem = unitSystem;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public String getUnitSystem() {
        return unitSystem;
    }

    public void setUnitSystem(String unitSystem) {
        this.unitSystem = unitSystem;
    }
}
