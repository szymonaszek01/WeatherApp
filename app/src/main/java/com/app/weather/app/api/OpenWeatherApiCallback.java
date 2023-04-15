package com.app.weather.app.api;

public interface OpenWeatherApiCallback<T> {

    void onSuccess(T body);

    void onFailure(Exception e);
}
