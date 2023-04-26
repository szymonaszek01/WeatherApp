package com.app.weather.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import java.util.List;

public class WeeklyFragment extends Fragment {

    private MyViewModel myViewModel;

    private TextView textViewDaily;

    private ImageView imageViewWeeklyWeather;

    private TextView textViewSecondaryWeeklyTabTemp;

    private TextView textViewSecondaryWeeklyTabPressure;

    private TextView textViewSecondaryWeeklyTabWind;

    private TextView textViewSecondaryWeeklyTabHumidity;

    private int selectedIndex;

    private List<WeatherDetailsBase> weatherDetailsBaseList;

    private WeatherDetailsBase selectedWeatherDetailsBase = null;

    private double slideStartX = 0;

    private double slideEndX = 0;

    public WeeklyFragment() {
    }

    public static WeeklyFragment newInstance() {
        WeeklyFragment fragment = new WeeklyFragment();
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
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);

        selectedIndex = 1;
        textViewDaily = view.findViewById(R.id.textViewDaily);
        imageViewWeeklyWeather = view.findViewById(R.id.imageViewWeeklyWeather);

        textViewSecondaryWeeklyTabTemp = view.findViewById(R.id.textViewSecondaryWeeklyTabTemp);
        textViewSecondaryWeeklyTabHumidity = view.findViewById(R.id.textViewSecondaryWeeklyTabHumidity);
        textViewSecondaryWeeklyTabPressure = view.findViewById(R.id.textViewSecondaryWeeklyTabPressure);
        textViewSecondaryWeeklyTabWind = view.findViewById(R.id.textViewSecondaryWeeklyTabWind);

        view.setVisibility(View.INVISIBLE);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        onWeatherDetailsWeeklyObserve();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (selectedWeatherDetailsBase == null) {
                    return false;
                }

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        slideStartX = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        slideEndX = motionEvent.getX();
                        if ((slideEndX - slideStartX) > 0) {
                            // Left to right
                            selectedIndex = selectedIndex - 1 > 0 ? selectedIndex - 1 : 1;
                        } else {
                            // Right to left
                            selectedIndex = selectedIndex + 1 < 8 ? selectedIndex + 1 : 7;
                        }
                        break;
                }

                selectedWeatherDetailsBase = weatherDetailsBaseList.get(selectedIndex);
                updateTextViews();

                return true;
            }
        });

        return view;
    }

    private void onWeatherDetailsWeeklyObserve() {
        myViewModel.getWeatherDetailsWeekly().observe(getViewLifecycleOwner(), wDBaseList -> {
            weatherDetailsBaseList = wDBaseList;
            selectedWeatherDetailsBase = weatherDetailsBaseList.get(selectedIndex);

            updateTextViews();
        });
    }

    private void updateTextViews() {
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
    }
}