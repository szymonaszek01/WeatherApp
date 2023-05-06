package com.app.weather.app.model;

import java.util.List;

public class FavouriteCityList {

    List<String> favouriteCities;

    public FavouriteCityList(List<String> favouriteCities) {
        this.favouriteCities = favouriteCities;
    }

    public List<String> getFavouriteCities() {
        return favouriteCities;
    }

    public void setFavouriteCities(List<String> favouriteCities) {
        this.favouriteCities = favouriteCities;
    }
}
