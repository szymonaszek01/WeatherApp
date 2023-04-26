package com.app.weather.app.dto;

public class OpenWeatherDto {

    OpenWeatherGeoResponseDto openWeatherGeoResponseDto;

    OpenWeatherDataResponseDto openWeatherDataResponseDto;

    public OpenWeatherDto(OpenWeatherGeoResponseDto openWeatherGeoResponseDto, OpenWeatherDataResponseDto openWeatherDataResponseDto) {
        this.openWeatherGeoResponseDto = openWeatherGeoResponseDto;
        this.openWeatherDataResponseDto = openWeatherDataResponseDto;
    }

    public OpenWeatherGeoResponseDto getOpenWeatherGeoResponseDto() {
        return openWeatherGeoResponseDto;
    }

    public void setOpenWeatherGeoResponseDto(OpenWeatherGeoResponseDto openWeatherGeoResponseDto) {
        this.openWeatherGeoResponseDto = openWeatherGeoResponseDto;
    }

    public OpenWeatherDataResponseDto getOpenWeatherDataResponseDto() {
        return openWeatherDataResponseDto;
    }

    public void setOpenWeatherDataResponseDto(OpenWeatherDataResponseDto openWeatherDataResponseDto) {
        this.openWeatherDataResponseDto = openWeatherDataResponseDto;
    }
}
