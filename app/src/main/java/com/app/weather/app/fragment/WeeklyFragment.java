package com.app.weather.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.weather.app.R;
import com.app.weather.app.model.WeatherDetailsBase;
import com.app.weather.app.util.ConstantUtil;
import com.app.weather.app.util.OpenWeatherUtil;
import com.app.weather.app.viewmodel.MyViewModel;
import com.bumptech.glide.Glide;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class WeeklyFragment extends Fragment {

    private MyViewModel myViewModel;

    private int pageNumber;

    private TextView textViewDaily;

    private ImageView imageViewWeeklyWeather;

    private TextView textViewSecondaryWeeklyTabTemp;

    private TextView textViewSecondaryWeeklyTabPressure;

    private TextView textViewSecondaryWeeklyTabWind;

    private TextView textViewSecondaryWeeklyTabHumidity;

    private WeatherDetailsBase selectedWeatherDetailsBase = null;

    public WeeklyFragment() {
    }

    public static WeeklyFragment newInstance(Integer pageNumber) {
        WeeklyFragment fragment = new WeeklyFragment();
        Bundle args = new Bundle();
        args.putInt("pageNumber", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageNumber = getArguments().getInt("pageNumber");
            myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);

        textViewDaily = view.findViewById(R.id.textViewDaily);
        imageViewWeeklyWeather = view.findViewById(R.id.imageViewWeeklyWeather);

        textViewSecondaryWeeklyTabTemp = view.findViewById(R.id.textViewSecondaryWeeklyTabTemp);
        textViewSecondaryWeeklyTabHumidity = view.findViewById(R.id.textViewSecondaryWeeklyTabHumidity);
        textViewSecondaryWeeklyTabPressure = view.findViewById(R.id.textViewSecondaryWeeklyTabPressure);
        textViewSecondaryWeeklyTabWind = view.findViewById(R.id.textViewSecondaryWeeklyTabWind);

        view.setVisibility(View.INVISIBLE);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        onWeatherDetailsWeeklyObserve();

        return view;
    }

    private void onWeatherDetailsWeeklyObserve() {
        myViewModel.getOpenWeatherDto().observe(getViewLifecycleOwner(), data -> {
            selectedWeatherDetailsBase = OpenWeatherUtil.getInstance().weatherDetailsBaseListMapper(data.getOpenWeatherDataResponseDto()).get(pageNumber + 1);

            LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(selectedWeatherDetailsBase.getDt(), 0, OffsetDateTime.now().getOffset());
            String dayName = localDateTime.getDayOfWeek().toString().toLowerCase();
            textViewDaily.setText(dayName.substring(0, 1).toUpperCase() + dayName.substring(1));

            String iconId = selectedWeatherDetailsBase.getIcon();
            String url = "https://openweathermap.org/img/wn/" + iconId + "@4x.png";
            Glide.with(getActivity()).load(url).into(imageViewWeeklyWeather);

            textViewSecondaryWeeklyTabTemp.setText(OpenWeatherUtil.getInstance().temperatureUnitSystemConverter(selectedWeatherDetailsBase.getMain().getTemp(), selectedWeatherDetailsBase.getUnitSystem(), ConstantUtil.SPACE));
            textViewSecondaryWeeklyTabWind.setText(OpenWeatherUtil.getInstance().windSpeedUnitSystemConverter(selectedWeatherDetailsBase.getMain().getWind(), selectedWeatherDetailsBase.getUnitSystem(), ConstantUtil.NEW_LINE));
            textViewSecondaryWeeklyTabPressure.setText(selectedWeatherDetailsBase.getMain().getPressure() + "\nhPa");
            textViewSecondaryWeeklyTabHumidity.setText(selectedWeatherDetailsBase.getMain().getHumidity() + " %");

            View view = getView();
            if (view != null) {
                view.setVisibility(View.VISIBLE);
            }
        });
    }
}