package com.app.weather.app.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.weather.app.dto.OpenWeatherDto;

public class MyViewModel extends ViewModel {

    private final MutableLiveData<OpenWeatherDto> openWeatherDto = new MutableLiveData<>();

    private final MutableLiveData<String> selectedUnitSystem = new MutableLiveData<>();

    private final MutableLiveData<String> selectedRefreshingInterval = new MutableLiveData<>();

    public MutableLiveData<OpenWeatherDto> getOpenWeatherDto() {
        return openWeatherDto;
    }

    public void setOpenWeatherDto(OpenWeatherDto openWeatherDto) {
        this.openWeatherDto.setValue(openWeatherDto);
    }

    public MutableLiveData<String> getSelectedUnitSystem() {
        return selectedUnitSystem;
    }

    public void setSelectedUnitSystem(String selectedUnitSystem) {
        this.selectedUnitSystem.setValue(selectedUnitSystem);
    }

    public MutableLiveData<String> getSelectedRefreshingInterval() {
        return selectedRefreshingInterval;
    }

    public void setSelectedRefreshingInterval(String selectedRefreshingInterval) {
        this.selectedRefreshingInterval.setValue(selectedRefreshingInterval);
    }
}
