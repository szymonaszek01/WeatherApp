package com.app.weather.app.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.weather.app.fragment.WeeklyFragment;

public class WeeklySliderAdapter extends FragmentStateAdapter {

    private Integer numOfPages;

    public WeeklySliderAdapter(@NonNull FragmentActivity activity, Integer numOfPages) {
        super(activity);
        this.numOfPages = numOfPages;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return WeeklyFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return numOfPages;
    }
}
