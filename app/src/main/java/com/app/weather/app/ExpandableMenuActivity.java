package com.app.weather.app;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.app.weather.app.model.FavouriteCityList;
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

        lastSelectedCityName = FileStorageUtil.getInstance().getLastSelectedCityName();
        lastSelectedUnitSystem = FileStorageUtil.getInstance().getLastSelectedUnitSystem();

        OpenWeatherUtil.getInstance().getUnitSystems(expandableListDetail);
        OpenWeatherUtil.getInstance().getFavouriteCities(expandableListDetail);
        OpenWeatherUtil.getInstance().getIntervals(expandableListDetail);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

        ExpandableListView expandableListView = findViewById(R.id.expandableListView);
        expandableListView.setGroupIndicator(Drawable.createFromPath(null));
        expandableListView.setAdapter(new ExpandableListAdapter() {
            @Override
            public Object getChild(int listPosition, int expandedListPosition) {
                List<String> cities = expandableListDetail.get(expandableListTitle.get(listPosition));
                if (cities == null) {
                    return null;
                }

                return cities.get(expandedListPosition);
            }

            @Override
            public long getChildId(int listPosition, int expandedListPosition) {
                return expandedListPosition;
            }

            @Override
            public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                final String expandedListText = (String) getChild(listPosition, expandedListPosition);
                final String listTitle = (String) getGroup(listPosition);

                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (listTitle.equals("Unit system") || listTitle.equals("Refreshing interval")) {
                    convertView = layoutInflater.inflate(R.layout.list_item, null);
                } else {
                    convertView = layoutInflater.inflate(R.layout.list_item_with_icon, null);
                }

                TextView expandedListTextView = convertView.findViewById(R.id.expandedListItemTextView);
                expandedListTextView.setText(expandedListText);

                if (listTitle.equals("Favourite cities")) {
                    ImageView expandedListImageView = convertView.findViewById(R.id.expandedListItemImageView);

                    expandedListImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isUnitSystemTextView(expandedListTextView)) {
                                FileStorageUtil.getInstance().saveLastSelectedUnitSystem(expandedListTextView.getText().toString());
                            }

                            String cityToRemove = expandedListTextView.getText().toString();
                            List<String> cities = FileStorageUtil.getInstance().getFavouriteCityList().getFavouriteCities();
                            cities.remove(cityToRemove);
                            FileStorageUtil.getInstance().updateFavouriteCityList(new FavouriteCityList(cities));

                            if (!cityToRemove.equals(FileStorageUtil.getInstance().getLastSelectedCityName())) {
                                FileStorageUtil.getInstance().removeOpenWeatherDto(cityToRemove);
                            }

                            OpenWeatherUtil.getInstance().showToast(getApplicationContext(), "\"" + cityToRemove + "\" was removed from favourite city list");

                            startActivity(intent);
                        }
                    });
                }

                expandedListTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isUnitSystemTextView(expandedListTextView)) {
                            FileStorageUtil.getInstance().saveLastSelectedUnitSystem(expandedListTextView.getText().toString());

                            OpenWeatherUtil.getInstance().showToast(getApplicationContext(), "Selected \"" + expandedListTextView.getText().toString() + "\" unit system");
                        } else {
                            FileStorageUtil.getInstance().saveLastSelectedCityName(expandedListTextView.getText().toString());

                            OpenWeatherUtil.getInstance().showToast(getApplicationContext(), "Selected \"" + expandedListTextView.getText().toString() + "\"");
                        }

                        startActivity(intent);
                    }
                });

                return convertView;
            }

            @Override
            public int getChildrenCount(int listPosition) {
                List<String> cities = expandableListDetail.get(expandableListTitle.get(listPosition));
                if (cities == null) {
                    return 0;
                }

                return cities.size();
            }

            @Override
            public Object getGroup(int listPosition) {
                return expandableListTitle.get(listPosition);
            }

            @Override
            public void registerDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public int getGroupCount() {
                return expandableListTitle.size();
            }

            @Override
            public long getGroupId(int listPosition) {
                return listPosition;
            }

            @Override
            public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                String listTitle = (String) getGroup(listPosition);
                if (convertView == null) {
                    LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = layoutInflater.inflate(R.layout.list_group, null);
                }

                TextView listTitleTextView = convertView.findViewById(R.id.listTitle);
                listTitleTextView.setTextColor(Color.WHITE);
                listTitleTextView.setTextSize(25);
                listTitleTextView.setText(listTitle);
                listTitleTextView.setPadding(0, 35, 0, 35);

                return convertView;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public boolean isChildSelectable(int listPosition, int expandedListPosition) {
                return true;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public void onGroupExpanded(int i) {

            }

            @Override
            public void onGroupCollapsed(int i) {

            }

            @Override
            public long getCombinedChildId(long l, long l1) {
                return 0;
            }

            @Override
            public long getCombinedGroupId(long l) {
                return 0;
            }

            private boolean isUnitSystemTextView(TextView textView) {
                String text = textView.getText().toString();
                return text.equals("Metric") || text.equals("Imperial");
            }
        });
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