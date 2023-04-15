package com.app.weather.app;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.weather.app.api.OpenWeatherApiCallback;
import com.app.weather.app.api.OpenWeatherApiImpl;
import com.app.weather.app.dto.OpenWeatherDataResponseDto;
import com.app.weather.app.dto.OpenWeatherGeoResponseDto;
import com.app.weather.app.model.GeoDetails;
import com.app.weather.app.model.WeatherDetails;
import com.app.weather.app.util.constant.Constant;
import com.app.weather.app.util.mapper.OpenWeatherMapper;

public class MainActivity extends AppCompatActivity {

    private GeoDetails geoDetails;

    private WeatherDetails weatherDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onWeatherGeoResponse();
    }

    private void onWeatherGeoResponse() {
        OpenWeatherApiImpl.getInstance().getOpenWeatherGeo("Warszawa", new OpenWeatherApiCallback<OpenWeatherGeoResponseDto>() {
            @Override
            public void onSuccess(OpenWeatherGeoResponseDto body) {
                geoDetails = OpenWeatherMapper.getInstance().geoDetailsMapper(body);
                onWeatherDataResponse();

                Log.i(Constant.WEATHER_GEO_RESPONSE, body.toString());
            }

            @Override
            public void onFailure(Exception e) {
                Log.i(Constant.WEATHER_GEO_RESPONSE, e.getMessage());
            }
        });
    }

    private void onWeatherDataResponse() {
        OpenWeatherApiImpl.getInstance().getOpenWeatherData(geoDetails.getCoord().getLat(), geoDetails.getCoord().getLon(), new OpenWeatherApiCallback<OpenWeatherDataResponseDto>() {
            @Override
            public void onSuccess(OpenWeatherDataResponseDto body) {
                weatherDetails = OpenWeatherMapper.getInstance().weatherDetailsMapper(body);
                updateTextViews();

                Log.i(Constant.WEATHER_DATA_RESPONSE, body.toString());
            }

            @Override
            public void onFailure(Exception e) {
                Log.i(Constant.WEATHER_DATA_RESPONSE, e.getMessage());
            }
        });
    }

    private void updateTextViews() {
        TextView textViewGeoDetails = findViewById(R.id.textViewGeoDetails);
        TextView textViewWeatherDetails = findViewById(R.id.textViewWeatherDetails);

        textViewGeoDetails.setText("City: " + geoDetails.getCity() + ", Lat: " + geoDetails.getCoord().getLat() + ", Lon: " + geoDetails.getCoord().getLon());
        textViewWeatherDetails.setText("Temperature: " + weatherDetails.getMain().getTemp());
    }
}