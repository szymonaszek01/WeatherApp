package com.app.weather.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.weather.app.R;
import com.app.weather.app.util.ConstantUtil;
import com.app.weather.app.util.OpenWeatherUtil;
import com.app.weather.app.viewmodel.MyViewModel;

public class WeatherDetailsFragment extends Fragment {

    private HorizontalScrollView horizontalScrollView;

    private TextView textViewTemp;

    private TextView textViewPressure;

    private TextView textViewHumidity;

    private TextView textViewWind;

    private TextView textViewVisibility;

    private MyViewModel myViewModel;

    public WeatherDetailsFragment() {
    }

    public static WeatherDetailsFragment newInstance(String param1, String param2) {
        WeatherDetailsFragment fragment = new WeatherDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_details, container, false);

        textViewTemp = view.findViewById(R.id.textViewSecondaryTabTemp);
        textViewPressure = view.findViewById(R.id.textViewSecondaryTabPressure);
        textViewHumidity = view.findViewById(R.id.textViewSecondaryTabHumidity);
        textViewWind = view.findViewById(R.id.textViewSecondaryTabWind);
        textViewVisibility = view.findViewById(R.id.textViewSecondaryTabVisibility);
        horizontalScrollView = view.findViewById(R.id.horizontalScrollView);

        view.setVisibility(View.INVISIBLE);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        onWeatherDetailsCurrentObserve();

        return view;
    }

    private void onWeatherDetailsCurrentObserve() {
        myViewModel.getWeatherDetailsCurrent().observe(getViewLifecycleOwner(), weatherDetailsCurrent -> {
            String temperature = weatherDetailsCurrent != null ? OpenWeatherUtil.getInstance().temperatureUnitSystemConverter(weatherDetailsCurrent.getMain().getTemp(), weatherDetailsCurrent.getUnitSystem(), ConstantUtil.SPACE) : ConstantUtil.BLANK;
            String pressure = weatherDetailsCurrent != null ? weatherDetailsCurrent.getMain().getPressure() + " hPa" : ConstantUtil.BLANK;
            String humidity = weatherDetailsCurrent != null ? weatherDetailsCurrent.getMain().getHumidity() + " %" : ConstantUtil.BLANK;
            String wind = weatherDetailsCurrent != null ? OpenWeatherUtil.getInstance().windSpeedUnitSystemConverter(weatherDetailsCurrent.getMain().getWind(), weatherDetailsCurrent.getUnitSystem(), ConstantUtil.SPACE) : ConstantUtil.BLANK;
            String visibility = weatherDetailsCurrent != null ? weatherDetailsCurrent.getVisibility() + " m" : ConstantUtil.BLANK;

            textViewTemp.setText(temperature);
            textViewPressure.setText(pressure);
            textViewHumidity.setText(humidity);
            textViewWind.setText(wind);
            textViewVisibility.setText(visibility);

            View view = getView();
            if (view != null) {
                view.setVisibility(View.VISIBLE);
            }
        });
    }
}