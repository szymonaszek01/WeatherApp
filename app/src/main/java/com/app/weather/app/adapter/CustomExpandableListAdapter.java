package com.app.weather.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.weather.app.R;
import com.app.weather.app.model.FavouriteCityList;
import com.app.weather.app.util.FileStorageUtil;
import com.app.weather.app.util.OpenWeatherUtil;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter implements ExpandableListAdapter {

    private final List<String> listTitles;

    private HashMap<String, List<String>> listDetails = new HashMap<>();

    private final Context context;

    private final Intent intent;

    public CustomExpandableListAdapter(List<String> listTitles, HashMap<String, List<String>> listDetails, Context context, Intent intent) {
        this.listTitles = listTitles;
        this.listDetails = listDetails;
        this.context = context;
        this.intent = intent;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        List<String> listDetails = this.listDetails.get(listTitles.get(listPosition));
        if (listDetails == null) {
            return null;
        }

        return listDetails.get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        final String listTitle = (String) getGroup(listPosition);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

                    OpenWeatherUtil.getInstance().showToast(context, "\"" + cityToRemove + "\" was removed from favourite city list");

                    context.startActivity(intent);
                }
            });
        }

        expandedListTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUnitSystemTextView(expandedListTextView)) {
                    FileStorageUtil.getInstance().saveLastSelectedUnitSystem(expandedListTextView.getText().toString());

                    OpenWeatherUtil.getInstance().showToast(context, "Selected \"" + expandedListTextView.getText().toString() + "\" unit system");
                } else {
                    FileStorageUtil.getInstance().saveLastSelectedCityName(expandedListTextView.getText().toString());

                    OpenWeatherUtil.getInstance().showToast(context, "Selected \"" + expandedListTextView.getText().toString() + "\"");
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        List<String> cities = listDetails.get(listTitles.get(listPosition));
        if (cities == null) {
            return 0;
        }

        return cities.size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return listTitles.get(listPosition);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getGroupCount() {
        return listTitles.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

}
