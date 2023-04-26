package com.app.weather.app.model;

public class WeatherDetailsCurrent extends WeatherDetailsBase{

    private int visibility;

    private int timezoneOffset;

    public WeatherDetailsCurrent(Main main, String icon, long dt, String unitSystem, int visibility, int timezoneOffset) {
        super(main, icon, dt, unitSystem);
        this.visibility = visibility;
        this.timezoneOffset = timezoneOffset;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(int timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }
}
