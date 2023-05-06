package com.app.weather.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.weather.app.ExpandableMenuActivity;
import com.app.weather.app.R;
import com.app.weather.app.api.OpenWeatherApiCallback;
import com.app.weather.app.api.OpenWeatherApiImpl;
import com.app.weather.app.dto.OpenWeatherDataResponseDto;
import com.app.weather.app.dto.OpenWeatherDto;
import com.app.weather.app.dto.OpenWeatherGeoResponseDto;
import com.app.weather.app.util.ConstantUtil;
import com.app.weather.app.viewmodel.MyViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class NavBarFragment extends Fragment {

    private OpenWeatherDto openWeatherDto = null;

    private TextInputEditText textInputEditTextCityName;

    private MyViewModel myViewModel;

    public NavBarFragment() {
    }

    public static NavBarFragment newInstance() {
        NavBarFragment fragment = new NavBarFragment();
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
        View view = inflater.inflate(R.layout.fragment_nav_bar, container, false);

        textInputEditTextCityName = view.findViewById(R.id.textInputEditText);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        openWeatherDto = new OpenWeatherDto(null, null);

        onTextInputEditTextEventListener();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onHamburgerEventListener(view);
    }

    private void onHamburgerEventListener(View view) {
        Intent intent = new Intent(getActivity(), ExpandableMenuActivity.class);

        ImageView imageView = view.findViewById(R.id.hamburger);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void onTextInputEditTextEventListener() {
        textInputEditTextCityName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (isIMEActionDoneOrNext(i)) {
                    if (!isTextViewEmpty(textView)) {
                        onWeatherGeoResponse(textView.getText().toString());
                    }

                    hideKeyboard(getActivity());
                    requireView().clearFocus();

                    return true;
                }
                return false;
            }

            private void onWeatherGeoResponse(String cityName) {
                OpenWeatherApiImpl.getInstance().getOpenWeatherGeo(cityName, new OpenWeatherApiCallback<OpenWeatherGeoResponseDto>() {
                    @Override
                    public void onSuccess(OpenWeatherGeoResponseDto body) {
                        openWeatherDto.setOpenWeatherGeoResponseDto(body);
                        onWeatherDataResponse(body.getLat(), body.getLon());
                        Log.i(ConstantUtil.WEATHER_GEO_RESPONSE, body.toString());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.i(ConstantUtil.WEATHER_GEO_RESPONSE, e.getMessage());
                    }
                });
            }

            private void onWeatherDataResponse(double lat, double lon) {
                OpenWeatherApiImpl.getInstance().getOpenWeatherData(lat, lon, new OpenWeatherApiCallback<OpenWeatherDataResponseDto>() {
                    @Override
                    public void onSuccess(OpenWeatherDataResponseDto body) {
                        openWeatherDto.setOpenWeatherDataResponseDto(body);
                        myViewModel.setOpenWeatherDto(openWeatherDto);
                        Log.i(ConstantUtil.WEATHER_DATA_RESPONSE, body.toString());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.i(ConstantUtil.WEATHER_DATA_RESPONSE, e.getMessage());
                    }
                });
            }

            private boolean isIMEActionDoneOrNext(int i) {
                return i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT;
            }

            private boolean isTextViewEmpty(TextView textView) {
                return textView.getText().toString().isEmpty();
            }

            private void hideKeyboard(Activity activity) {
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}