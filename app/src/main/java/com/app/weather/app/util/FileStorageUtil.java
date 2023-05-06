package com.app.weather.app.util;

import android.content.SharedPreferences;

import com.app.weather.app.dto.OpenWeatherDto;
import com.app.weather.app.model.FavouriteCityList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Set;

public class FileStorageUtil {

    private static FileStorageUtil fileStorageUtil;

    private final SharedPreferences sharedPreferences;

    private FileStorageUtil(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static void createInstance(SharedPreferences sharedPreferences) {
        if (fileStorageUtil == null) {
            fileStorageUtil = new FileStorageUtil(sharedPreferences);
        }
    }

    public static FileStorageUtil getInstance() {
        return fileStorageUtil;
    }

    public String getLastSelectedUnitSystem() {
        String unitSystem = sharedPreferences.getString(ConstantUtil.LAST_SELECTED_UNIT_SYSTEM, ConstantUtil.UNIT_SYSTEM_NOT_FOUND);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (ConstantUtil.UNIT_SYSTEM_NOT_FOUND.equals(unitSystem)) {
            return null;
        }

        return gson.fromJson(unitSystem, String.class);
    }

    public void saveLastSelectedUnitSystem(String unitSystem) {
        if (unitSystem == null || unitSystem.isEmpty()) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        editor.putString(ConstantUtil.LAST_SELECTED_UNIT_SYSTEM, gson.toJson(unitSystem)).apply();
    }

    public OpenWeatherDto getOpenWeatherDto(String cityName) {
        if (cityName == null || cityName.isEmpty()) {
            return null;
        }

        String openWeatherDto = sharedPreferences.getString(cityName, ConstantUtil.OPEN_WEATHER_DTO_NOT_FOUND);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return ConstantUtil.OPEN_WEATHER_DTO_NOT_FOUND.equals(openWeatherDto) ? null : gson.fromJson(openWeatherDto, OpenWeatherDto.class);
    }

    public void saveOpenWeatherDto(OpenWeatherDto openWeatherDto) {
        if (openWeatherDto == null) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        editor.putString(openWeatherDto.getOpenWeatherGeoResponseDto().getName(), gson.toJson(openWeatherDto)).apply();
    }

    public void removeOpenWeatherDto(String cityName) {
        if (cityName == null || cityName.isEmpty()) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(cityName).apply();
    }

    public String getLastSelectedCityName() {
        String cityName = sharedPreferences.getString(ConstantUtil.LAST_SELECTED_CITY_NAME, ConstantUtil.CITY_NAME_NOT_FOUND);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (ConstantUtil.CITY_NAME_NOT_FOUND.equals(cityName)) {
            return null;
        }

        return gson.fromJson(cityName, String.class);
    }

    public void saveLastSelectedCityName(String cityName) {
        if (cityName == null || cityName.isEmpty()) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        editor.putString(ConstantUtil.LAST_SELECTED_CITY_NAME, gson.toJson(cityName)).apply();
    }

    public FavouriteCityList getFavouriteCityList() {
        String favouriteCityList = sharedPreferences.getString(ConstantUtil.FAVOURITE_CITY_LIST, ConstantUtil.FAVOURITE_CITY_LIST_NOT_FOUND);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (ConstantUtil.FAVOURITE_CITY_LIST_NOT_FOUND.equals(favouriteCityList)) {
            return null;
        }

        return gson.fromJson(favouriteCityList, FavouriteCityList.class);
    }

    public void updateFavouriteCityList(FavouriteCityList favouriteCityList) {
        if (favouriteCityList == null) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        editor.putString(ConstantUtil.FAVOURITE_CITY_LIST, gson.toJson(favouriteCityList)).apply();
    }

    public Set<String> getAllKeys() {
        return sharedPreferences.getAll().keySet();
    }
}
