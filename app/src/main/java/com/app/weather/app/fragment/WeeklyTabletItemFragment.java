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

public class WeeklyTabletItemFragment extends Fragment {

    private Integer day;

    private MyViewModel myViewModel;

    private TextView dayTextView;

    private TextView tempTextView;

    private TextView windTextView;

    private TextView humidTextView;

    private TextView pressTextView;

    private ImageView weatherIcon;

    private WeatherDetailsBase selectedWeatherDetailsBase = null;

    private String selectedUnitSystem = null;

    public WeeklyTabletItemFragment() {
    }

    public static WeeklyTabletItemFragment newInstance(int day) {
        WeeklyTabletItemFragment fragment = new WeeklyTabletItemFragment();
        Bundle args = new Bundle();
        args.putInt("day", day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = getArguments().getInt("day");
        }
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_tablet_item, container, false);

        dayTextView = view.findViewById(R.id.day_name_text);
        tempTextView = view.findViewById(R.id.temperature_text_view);
        windTextView = view.findViewById(R.id.wind_text_view);
        humidTextView = view.findViewById(R.id.humidity_text_view);
        pressTextView = view.findViewById(R.id.pressure_text_view);
        weatherIcon = view.findViewById(R.id.weather_icon2);

        selectedWeatherDetailsBase = OpenWeatherUtil.getInstance().weatherDetailsBaseListMapper(myViewModel.getOpenWeatherDto().getValue().getOpenWeatherDataResponseDto()).get(day + 1);
        selectedUnitSystem = myViewModel.getSelectedUnitSystem().getValue();
        updateFragment();

        return view;
    }

    private void updateFragment() {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(selectedWeatherDetailsBase.getDt(), 0, OffsetDateTime.now().getOffset());
        String dayName = localDateTime.getDayOfWeek().toString().toLowerCase();
        dayTextView.setText(dayName.substring(0, 1).toUpperCase() + dayName.substring(1));

        String iconId = selectedWeatherDetailsBase.getIcon();
        String url = "https://openweathermap.org/img/wn/" + iconId + "@4x.png";
        Glide.with(getActivity()).load(url).into(weatherIcon);

        tempTextView.setText(OpenWeatherUtil.getInstance().temperatureUnitSystemConverter(selectedWeatherDetailsBase.getMain().getTemp(), selectedUnitSystem, ConstantUtil.SPACE));
        windTextView.setText(OpenWeatherUtil.getInstance().windSpeedUnitSystemConverter(selectedWeatherDetailsBase.getMain().getWind(), selectedUnitSystem, ConstantUtil.NEW_LINE));
        pressTextView.setText(selectedWeatherDetailsBase.getMain().getPressure() + "\nhPa");
        humidTextView.setText(selectedWeatherDetailsBase.getMain().getHumidity() + " %");

        View view = getView();
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }
}