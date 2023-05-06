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
import com.app.weather.app.model.WeatherDetailsCurrent;
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

    private WeatherDetailsCurrent selectedWeatherDetailsCurrent = null;

    private String selectedUnitSystem = null;

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
        onSelectedUnitSystemObserve();

        return view;
    }

    private void onWeatherDetailsCurrentObserve() {
        myViewModel.getOpenWeatherDto().observe(getViewLifecycleOwner(), data -> {
            selectedWeatherDetailsCurrent = OpenWeatherUtil.getInstance().weatherDetailsCurrentMapper(data.getOpenWeatherDataResponseDto());
            updateFragment();
        });
    }

    private void onSelectedUnitSystemObserve() {
        myViewModel.getSelectedUnitSystem().observe(getViewLifecycleOwner(), data -> {
            selectedUnitSystem = data;
            updateFragment();
        });
    }

    private void updateFragment() {
        String temperature = selectedWeatherDetailsCurrent != null ? OpenWeatherUtil.getInstance().temperatureUnitSystemConverter(selectedWeatherDetailsCurrent.getMain().getTemp(), selectedUnitSystem, ConstantUtil.SPACE) : ConstantUtil.BLANK;
        String pressure = selectedWeatherDetailsCurrent != null ? selectedWeatherDetailsCurrent.getMain().getPressure() + " hPa" : ConstantUtil.BLANK;
        String humidity = selectedWeatherDetailsCurrent != null ? selectedWeatherDetailsCurrent.getMain().getHumidity() + " %" : ConstantUtil.BLANK;
        String wind = selectedWeatherDetailsCurrent != null ? OpenWeatherUtil.getInstance().windSpeedUnitSystemConverter(selectedWeatherDetailsCurrent.getMain().getWind(), selectedUnitSystem, ConstantUtil.SPACE) : ConstantUtil.BLANK;
        String visibility = selectedWeatherDetailsCurrent != null ? selectedWeatherDetailsCurrent.getVisibility() + " m" : ConstantUtil.BLANK;

        textViewTemp.setText(temperature);
        textViewPressure.setText(pressure);
        textViewHumidity.setText(humidity);
        textViewWind.setText(wind);
        textViewVisibility.setText(visibility);

        View view = getView();
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }
}