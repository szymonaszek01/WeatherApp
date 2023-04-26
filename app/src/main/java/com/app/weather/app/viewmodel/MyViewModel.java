package com.app.weather.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.weather.app.model.GeoDetails;
import com.app.weather.app.model.WeatherDetailsBase;
import com.app.weather.app.model.WeatherDetailsCurrent;

import java.util.List;

public class MyViewModel extends ViewModel {

    private final MutableLiveData<GeoDetails> geoDetails = new MutableLiveData<>();

    private final MutableLiveData<WeatherDetailsCurrent> weatherDetailsCurrent = new MutableLiveData<>();

    private final MutableLiveData<List<WeatherDetailsBase>> weatherDetailsWeekly = new MutableLiveData<>();

    public LiveData<GeoDetails> getGeoDetails() {
        return geoDetails;
    }

    public void setGeoDetails(GeoDetails geoDetails) {
        this.geoDetails.setValue(geoDetails);
    }

    public LiveData<WeatherDetailsCurrent> getWeatherDetailsCurrent() {
        return weatherDetailsCurrent;
    }

    public void setWeatherDetailsCurrent(WeatherDetailsCurrent weatherDetailsCurrent) {
        this.weatherDetailsCurrent.setValue(weatherDetailsCurrent);
    }

    public LiveData<List<WeatherDetailsBase>> getWeatherDetailsWeekly() {
        return weatherDetailsWeekly;
    }

    public void setWeatherDetails(List<WeatherDetailsBase> weatherDetailsList) {
        this.weatherDetailsWeekly.setValue(weatherDetailsList);
    }
}
