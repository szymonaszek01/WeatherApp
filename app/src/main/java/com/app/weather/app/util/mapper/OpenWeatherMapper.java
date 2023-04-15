package com.app.weather.app.util.mapper;

import com.app.weather.app.dto.OpenWeatherDataResponseDto;
import com.app.weather.app.dto.OpenWeatherGeoResponseDto;
import com.app.weather.app.model.Coord;
import com.app.weather.app.model.GeoDetails;
import com.app.weather.app.model.WeatherDetails;

import java.util.Arrays;

public class OpenWeatherMapper {

    private static OpenWeatherMapper openWeatherMapper = null;

    public static OpenWeatherMapper getInstance() {
        if (openWeatherMapper == null) {
            openWeatherMapper = new OpenWeatherMapper();
        }

        return openWeatherMapper;
    }

    public GeoDetails geoDetailsMapper(OpenWeatherGeoResponseDto openWeatherGeoResponseDto) {
        return new GeoDetails(
                openWeatherGeoResponseDto.getCountry(),
                openWeatherGeoResponseDto.getName(),
                new Coord(openWeatherGeoResponseDto.getLon(), openWeatherGeoResponseDto.getLat())
        );
    }

    public WeatherDetails weatherDetailsMapper(OpenWeatherDataResponseDto openWeatherDataResponseDto) {
        return new WeatherDetails(
                Arrays.asList(openWeatherDataResponseDto.getWeather()),
                openWeatherDataResponseDto.getMain(),
                openWeatherDataResponseDto.getWind(),
                openWeatherDataResponseDto.getRain(),
                openWeatherDataResponseDto.getClouds()
        );
    }
}
