package org.sberstart.cities;

import org.sberstart.cities.model.City;

import java.util.ArrayList;
import java.util.List;

public class CityRepository {
    private final List<City> cities;

    public CityRepository() {
        this.cities = new ArrayList<>();
    }

    public List<City> getCitiesList() {
        return cities;
    }

    public void add(City city) {
        if (city != null) {
            cities.add(city);
        }
    }

    public void remove(City city) {
        cities.remove(city);
    }
}
