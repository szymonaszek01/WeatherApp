package com.app.weather.app.api;

import androidx.annotation.NonNull;

import com.app.weather.app.dto.OpenWeatherDataResponseDto;
import com.app.weather.app.dto.OpenWeatherGeoResponseDto;
import com.app.weather.app.util.ConstantUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherApiImpl {

    private static OpenWeatherApiImpl openWeatherApiImpl = null;

    private static OpenWeatherApi openWeatherApi;

    private OpenWeatherApiImpl() {
        openWeatherApi = new retrofit2.Retrofit.Builder()
                .baseUrl(ConstantUtil.OPEN_WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApi.class);
    }

    public static OpenWeatherApiImpl getInstance() {
        if (openWeatherApiImpl == null) {
            openWeatherApiImpl = new OpenWeatherApiImpl();
        }

        return openWeatherApiImpl;
    }

    public void getOpenWeatherGeo(String cityName, OpenWeatherApiCallback<OpenWeatherGeoResponseDto> callback) {
        openWeatherApi.getOpenWeatherGeo(cityName).enqueue(new Callback<List<OpenWeatherGeoResponseDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<OpenWeatherGeoResponseDto>> call, @NonNull Response<List<OpenWeatherGeoResponseDto>> response) {
                if (response.body() == null) {
                    callback.onFailure(new Exception(ConstantUtil.WEATHER_GEO_NOT_FOUND));
                    return;
                }

                callback.onSuccess(response.body().get(0));
            }

            @Override
            public void onFailure(@NonNull Call<List<OpenWeatherGeoResponseDto>> call, @NonNull Throwable t) {
                callback.onFailure(new Exception(ConstantUtil.RESPONSE_FAILED));
            }
        });
    }

    public void getOpenWeatherData(Double lat, Double lon, OpenWeatherApiCallback<OpenWeatherDataResponseDto> callback) {
        openWeatherApi.getOpenWeatherData(lat, lon).enqueue(new Callback<OpenWeatherDataResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<OpenWeatherDataResponseDto> call, @NonNull Response<OpenWeatherDataResponseDto> response) {
                if (response.body() == null) {
                    callback.onFailure(new Exception(ConstantUtil.WEATHER_DATA_NOT_FOUND));
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OpenWeatherDataResponseDto> call, @NonNull Throwable t) {
                callback.onFailure(new Exception(ConstantUtil.RESPONSE_FAILED));
            }
        });
    }
}
