package com.app.weather.app.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.weather.app.dto.OpenWeatherDto;

public class MyViewModel extends ViewModel {

    private final MutableLiveData<OpenWeatherDto> openWeatherDto = new MutableLiveData<>();

    public MutableLiveData<OpenWeatherDto> getOpenWeatherDto() {
        return openWeatherDto;
    }

    public void setOpenWeatherDto(OpenWeatherDto openWeatherDto) {
        this.openWeatherDto.setValue(openWeatherDto);
    }
}
