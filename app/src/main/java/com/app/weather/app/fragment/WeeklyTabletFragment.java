package com.app.weather.app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.weather.app.R;
import com.app.weather.app.model.WeatherDetailsBase;
import com.app.weather.app.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class WeeklyTabletFragment extends Fragment {

    private FragmentManager fragmentManager;

    private MyViewModel myViewModel;

    private List<WeatherDetailsBase> weatherDetailsBaseList = new ArrayList<>();

    public WeeklyTabletFragment() {
    }

    public static WeeklyTabletFragment newInstance() {
        WeeklyTabletFragment fragment = new WeeklyTabletFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_tablet, container, false);
        setDailyItems();
        fragmentManager = getChildFragmentManager();

        return view;
    }

    private void setDailyItems() {
        myViewModel.getOpenWeatherDto().observe(requireActivity(), data -> {

            WeeklyTabletItemFragment dayOne = WeeklyTabletItemFragment.newInstance(1);
            WeeklyTabletItemFragment dayTwo = WeeklyTabletItemFragment.newInstance(2);
            WeeklyTabletItemFragment dayThree = WeeklyTabletItemFragment.newInstance(3);
            WeeklyTabletItemFragment dayFour = WeeklyTabletItemFragment.newInstance(4);
            WeeklyTabletItemFragment dayFive = WeeklyTabletItemFragment.newInstance(5);
            WeeklyTabletItemFragment daySix = WeeklyTabletItemFragment.newInstance(6);

            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.day_one, dayOne, null)
                    .replace(R.id.day_two, dayTwo, null)
                    .replace(R.id.day_three, dayThree, null)
                    .replace(R.id.day_four, dayFour, null)
                    .replace(R.id.day_five, dayFive, null)
                    .replace(R.id.day_six, daySix, null)
                    .commit();

        });

    }
}