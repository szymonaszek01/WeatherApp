package com.app.weather.app.api;

import com.app.weather.app.dto.OpenWeatherDataResponseDto;
import com.app.weather.app.dto.OpenWeatherGeoResponseDto;
import com.app.weather.app.util.ConstantUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    @GET("/geo/1.0/direct?&appid=" + ConstantUtil.OPEN_WEATHER_API_KEY)
    Call<List<OpenWeatherGeoResponseDto>> getOpenWeatherGeo(@Query("q") String cityName);

    @GET("/data/2.5/onecall?&exclude=minutely,hourly,alerts&appid=" + ConstantUtil.OPEN_WEATHER_API_KEY + "&units=metric")
    Call<OpenWeatherDataResponseDto> getOpenWeatherData(@Query("lat") Double lat, @Query("lon") Double lon);
}
