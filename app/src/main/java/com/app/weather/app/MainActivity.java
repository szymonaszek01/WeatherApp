package com.app.weather.app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.app.weather.app.adapter.WeeklySliderAdapter;
import com.app.weather.app.api.OpenWeatherApiCallback;
import com.app.weather.app.api.OpenWeatherApiImpl;
import com.app.weather.app.dto.OpenWeatherDataResponseDto;
import com.app.weather.app.dto.OpenWeatherDto;
import com.app.weather.app.dto.OpenWeatherGeoResponseDto;
import com.app.weather.app.fragment.GeoDetailsFragment;
import com.app.weather.app.fragment.NavBarFragment;
import com.app.weather.app.fragment.WeatherDetailsFragment;
import com.app.weather.app.util.ConstantUtil;
import com.app.weather.app.util.FileStorageUtil;
import com.app.weather.app.util.OpenWeatherUtil;
import com.app.weather.app.viewmodel.MyViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private OpenWeatherDto lastSelectedOpenWeatherDto = null;

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    private ViewPager2 viewPager;

    private FragmentStateAdapter pagerAdapter;

    private final Integer numOfPages = 6;

    private String lastSelectedUnitSystem = null;

    private String lastSelectedRefreshingInterval = null;

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

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new WeeklySliderAdapter(this, numOfPages);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setFocusedByDefault(false);
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.nav_bar_fragment, NavBarFragment.class, null)
                .replace(R.id.geo_details_fragment, GeoDetailsFragment.class, null)
                .replace(R.id.weather_details_fragment, WeatherDetailsFragment.class, null)
                .commit();

        if (FileStorageUtil.getInstance().getLastSelectedCityName() != null) {
            lastSelectedOpenWeatherDto = FileStorageUtil.getInstance().getOpenWeatherDto(FileStorageUtil.getInstance().getLastSelectedCityName());
            lastSelectedUnitSystem = FileStorageUtil.getInstance().getLastSelectedUnitSystem();
            lastSelectedRefreshingInterval = FileStorageUtil.getInstance().getLastSelectedRefreshingInterval();

            myViewModel.setOpenWeatherDto(lastSelectedOpenWeatherDto);
            myViewModel.setSelectedUnitSystem(lastSelectedUnitSystem);
            myViewModel.setSelectedRefreshingInterval(lastSelectedRefreshingInterval);

            viewPager.setCurrentItem(0);
        }

        if (OpenWeatherUtil.getInstance().isConnectedToNetwork(getApplicationContext()) && lastSelectedOpenWeatherDto != null) {
            onWeatherGeoResponse(lastSelectedOpenWeatherDto.getOpenWeatherGeoResponseDto().getName());
        } else {
            OpenWeatherUtil.getInstance().showToast(getApplicationContext(), "No network connection");
        }

        myViewModel.getOpenWeatherDto().observe(this, data -> lastSelectedOpenWeatherDto = data);
        myViewModel.getSelectedUnitSystem().observe(this, data -> lastSelectedUnitSystem = data);
        myViewModel.getSelectedRefreshingInterval().observe(this, data -> lastSelectedRefreshingInterval = data);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (lastSelectedOpenWeatherDto != null) {
                    onWeatherGeoResponse(lastSelectedOpenWeatherDto.getOpenWeatherGeoResponseDto().getName());
                }
            }
        }, 0, Long.parseLong(lastSelectedRefreshingInterval != null ? lastSelectedRefreshingInterval : ConstantUtil.INTERVAL) * 1000 * 60);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (lastSelectedOpenWeatherDto != null) {
            FileStorageUtil.getInstance().saveLastSelectedCityName(lastSelectedOpenWeatherDto.getOpenWeatherGeoResponseDto().getName());
            FileStorageUtil.getInstance().saveLastSelectedUnitSystem(lastSelectedUnitSystem);
            FileStorageUtil.getInstance().saveOpenWeatherDto(lastSelectedOpenWeatherDto);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer.purge();
    }

    private void onWeatherGeoResponse(String cityName) {
        OpenWeatherApiImpl.getInstance().getOpenWeatherGeo(cityName, new OpenWeatherApiCallback<OpenWeatherGeoResponseDto>() {
            @Override
            public void onSuccess(OpenWeatherGeoResponseDto body) {
                lastSelectedOpenWeatherDto.setOpenWeatherGeoResponseDto(body);
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
                lastSelectedOpenWeatherDto.setOpenWeatherDataResponseDto(body);
                myViewModel.setOpenWeatherDto(lastSelectedOpenWeatherDto);
                Log.i(ConstantUtil.WEATHER_DATA_RESPONSE, body.toString());
            }

            @Override
            public void onFailure(Exception e) {
                Log.i(ConstantUtil.WEATHER_DATA_RESPONSE, e.getMessage());
            }
        });
    }
}