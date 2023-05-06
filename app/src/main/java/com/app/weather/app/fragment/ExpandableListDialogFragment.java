package com.app.weather.app.fragment;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.weather.app.R;
import com.app.weather.app.adapter.CustomExpandableListAdapter;
import com.app.weather.app.util.FileStorageUtil;
import com.app.weather.app.util.OpenWeatherUtil;
import com.app.weather.app.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDialogFragment extends DialogFragment {

    private String lastSelectedCityName = null;

    private String lastSelectedUnitSystem = null;

    private List<String> expandableListTitle;

    private final HashMap<String, List<String>> expandableListDetail = new HashMap<>();

    private MyViewModel myViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialog);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expanable_list_dialog, container, false);

        lastSelectedCityName = FileStorageUtil.getInstance().getLastSelectedCityName();
        lastSelectedUnitSystem = FileStorageUtil.getInstance().getLastSelectedUnitSystem();
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        OpenWeatherUtil.getInstance().getUnitSystems(expandableListDetail);
        OpenWeatherUtil.getInstance().getFavouriteCities(expandableListDetail);
        OpenWeatherUtil.getInstance().getIntervals(expandableListDetail);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

        ExpandableListView expandableListView = view.findViewById(R.id.expandableListView);
        expandableListView.setGroupIndicator(Drawable.createFromPath(null));
        expandableListView.setAdapter(new CustomExpandableListAdapter(expandableListTitle, expandableListDetail, requireContext(), this, myViewModel));
        onHamburgerEventListener(view);

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismiss();
            }
        };
    }

    private void onHamburgerEventListener(View view) {
        ImageView imageView = view.findViewById(R.id.hamburgerExpandableMenu);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
