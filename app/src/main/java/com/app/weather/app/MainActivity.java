package com.app.weather.app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.weather.app.api.OpenWeatherApiCallback;
import com.app.weather.app.api.OpenWeatherApiImpl;
import com.app.weather.app.dto.OpenWeatherDataResponseDto;
import com.app.weather.app.dto.OpenWeatherDto;
import com.app.weather.app.dto.OpenWeatherGeoResponseDto;
import com.app.weather.app.model.FavouriteCityList;
import com.app.weather.app.util.ConstantUtil;
import com.app.weather.app.util.FileStorageUtil;
import com.app.weather.app.util.OpenWeatherUtil;
import com.app.weather.app.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private OpenWeatherDto openWeatherDto = null;

    private String lastSelectedCityName = null;

    private String lastSelectedUnitSystem = null;

    private MyViewModel myViewModel;

    private final Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        FileStorageUtil.createInstance(this.getPreferences(Context.MODE_PRIVATE));

        // --- TO REMOVE AFTER FIRST RUN --- //
//         FileStorageUtil.getInstance().getAllKeys().forEach(key -> FileStorageUtil.getInstance().removeOpenWeatherDto(key));
//         FileStorageUtil.getInstance().saveLastSelectedUnitSystem("Metric");
//         FileStorageUtil.getInstance().updateFavouriteCityList(new FavouriteCityList(new ArrayList<>()));
        // --- TO REMOVE AFTER FIRST RUN --- //

        lastSelectedCityName = FileStorageUtil.getInstance().getLastSelectedCityName();
        lastSelectedUnitSystem = FileStorageUtil.getInstance().getLastSelectedUnitSystem();

        if (lastSelectedCityName != null) {
            openWeatherDto = FileStorageUtil.getInstance().getOpenWeatherDto(lastSelectedCityName);
            myViewModel.setOpenWeatherDto(openWeatherDto);
        }

        if (OpenWeatherUtil.getInstance().isConnectedToNetwork(getApplicationContext())) {
            onWeatherGeoResponse(lastSelectedCityName);
        } else {
            OpenWeatherUtil.getInstance().showToast(getApplicationContext(), "No network connection");
        }

        myViewModel.getOpenWeatherDto().observe(this, data -> openWeatherDto = data);

//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                if (lastSelectedCityName != null) {
//                    onWeatherGeoResponse(lastSelectedCityName);
//                }
//            }
//        }, 0, ConstantUtil.INTERVAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (openWeatherDto != null) {
            FileStorageUtil.getInstance().saveLastSelectedCityName(openWeatherDto.getOpenWeatherGeoResponseDto().getName());
            FileStorageUtil.getInstance().saveLastSelectedUnitSystem(OpenWeatherUtil.getInstance().weatherDetailsCurrentMapper(openWeatherDto.getOpenWeatherDataResponseDto()).getUnitSystem());
            FileStorageUtil.getInstance().saveOpenWeatherDto(openWeatherDto);
        }
    }

    private void onWeatherGeoResponse(String cityName) {
        OpenWeatherApiImpl.getInstance().getOpenWeatherGeo(cityName, new OpenWeatherApiCallback<OpenWeatherGeoResponseDto>() {
            @Override
            public void onSuccess(OpenWeatherGeoResponseDto body) {
                openWeatherDto.setOpenWeatherGeoResponseDto(body);
                onWeatherDataResponse(body.getLat(), body.getLon());
                Log.i(ConstantUtil.WEATHER_GEO_RESPONSE, body.toString());
            }

            @Override
            public void onFailure(Exception e) {
                Log.i(ConstantUtil.WEATHER_GEO_RESPONSE, e.getMessage());
            }
        });
    }

    private void onWeatherDataResponse(double lat, double lon) {
        OpenWeatherApiImpl.getInstance().getOpenWeatherData(lat, lon, new OpenWeatherApiCallback<OpenWeatherDataResponseDto>() {
            @Override
            public void onSuccess(OpenWeatherDataResponseDto body) {
                openWeatherDto.setOpenWeatherDataResponseDto(body);
                myViewModel.setOpenWeatherDto(openWeatherDto);
                Log.i(ConstantUtil.WEATHER_DATA_RESPONSE, body.toString());
            }

            @Override
            public void onFailure(Exception e) {
                Log.i(ConstantUtil.WEATHER_DATA_RESPONSE, e.getMessage());
            }
        });
    }
}