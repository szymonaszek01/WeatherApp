package com.app.weather.app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.app.weather.app.adapter.CustomExpandableListAdapter;
import com.app.weather.app.util.FileStorageUtil;
import com.app.weather.app.util.OpenWeatherUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableMenuActivity extends AppCompatActivity {

    private Intent intent = null;

    private String lastSelectedCityName = null;

    private String lastSelectedUnitSystem = null;

    private List<String> expandableListTitle;

    private final HashMap<String, List<String>> expandableListDetail = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        onHamburgerEventListener();
        intent = new Intent(ExpandableMenuActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        lastSelectedCityName = FileStorageUtil.getInstance().getLastSelectedCityName();
        lastSelectedUnitSystem = FileStorageUtil.getInstance().getLastSelectedUnitSystem();

        OpenWeatherUtil.getInstance().getUnitSystems(expandableListDetail);
        OpenWeatherUtil.getInstance().getFavouriteCities(expandableListDetail);
        OpenWeatherUtil.getInstance().getIntervals(expandableListDetail);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

        ExpandableListView expandableListView = findViewById(R.id.expandableListView);
        expandableListView.setGroupIndicator(Drawable.createFromPath(null));
        expandableListView.setAdapter(new CustomExpandableListAdapter(expandableListTitle, expandableListDetail, getApplicationContext(), intent));
    }

    private void onHamburgerEventListener() {
        ImageView imageView = findViewById(R.id.hamburgerExpandableMenu);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}