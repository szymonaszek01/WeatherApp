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
import com.app.weather.app.util.ConstantUtil;
import com.app.weather.app.viewmodel.MyViewModel;
import com.bumptech.glide.Glide;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class GeoDetailsFragment extends Fragment {

    private TextView textViewCityName;

    private ImageView imageViewWeather;

    private TextView textViewDate;

    private TextView textViewTime;

    private MyViewModel myViewModel;

    private String cityName;

    private String cords;

    public GeoDetailsFragment() {
    }

    public static GeoDetailsFragment newInstance() {
        GeoDetailsFragment fragment = new GeoDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_geo_details, container, false);

        textViewCityName = view.findViewById(R.id.textViewCity);
        imageViewWeather = view.findViewById(R.id.imageViewWeather);

        textViewDate = view.findViewById(R.id.textViewDate);
        textViewTime = view.findViewById(R.id.textViewTime);

        view.setVisibility(View.INVISIBLE);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        onGeoDetailsObserve();
        onWeatherDetailsCurrentObserve();

        return view;
    }

    private void onGeoDetailsObserve() {
        myViewModel.getGeoDetails().observe(getViewLifecycleOwner(), geoDetails -> {
            String lat = ConstantUtil.LAT + (geoDetails != null ? String.valueOf(geoDetails.getCoord().getLat()) : ConstantUtil.BLANK);
            String lon = ConstantUtil.LON + (geoDetails != null ? String.valueOf(geoDetails.getCoord().getLon()) : ConstantUtil.BLANK);

            cityName = geoDetails != null ? geoDetails.getCity() : ConstantUtil.BLANK;
            cords = lat + ConstantUtil.COMA + ConstantUtil.SPACE + lon;
            textViewCityName.setText(cityName);

            onClickTextViewCityNameEventListener();
        });
    }

    private void onWeatherDetailsCurrentObserve() {
        myViewModel.getWeatherDetailsCurrent().observe(getViewLifecycleOwner(), weatherDetailsCurrent -> {
            LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(weatherDetailsCurrent.getDt(), 0, OffsetDateTime.now().getOffset());
            localDateTime = localDateTime.plusSeconds(weatherDetailsCurrent.getTimezoneOffset());

            String day = String.format("%02d", localDateTime.getDayOfMonth());
            String month = String.format("%02d", localDateTime.getMonthValue());
            String year = String.format("%04d", localDateTime.getYear());
            textViewDate.setText(day + ConstantUtil.DOT + month + ConstantUtil.DOT + year);

            String hour = String.format("%02d", localDateTime.getHour());
            String minute = String.format("%02d", localDateTime.getMinute());
            textViewTime.setText(hour + ":" + minute);

            String iconId = weatherDetailsCurrent.getIcon();
            String url = "https://openweathermap.org/img/wn/" + iconId + "@4x.png";
            Glide.with(getActivity()).load(url).into(imageViewWeather);

            View view = getView();
            if (view != null) {
                view.setVisibility(View.VISIBLE);
            }
        });
    }

    private void onClickTextViewCityNameEventListener() {
        textViewCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCityName = !textViewCityName.getText().toString().contains(ConstantUtil.LAT);
                textViewCityName.setText(isCityName ? cords : cityName);
                textViewCityName.setTextSize(isCityName ? 15 : 40);
            }
        });
    }
}