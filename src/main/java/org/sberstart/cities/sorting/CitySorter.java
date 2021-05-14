package org.sberstart.cities.sorting;

import org.sberstart.cities.CityRepository;
import org.sberstart.cities.model.City;

import java.util.List;

public class CitySorter {

  private SortingAlgorithm sortingAlgorithm;
  private List<City> cities;

  public CitySorter(SortingAlgorithm sortingAlgorithm, CityRepository cityRepository) {
    this.sortingAlgorithm = sortingAlgorithm;
    this.cities = cityRepository.getCitiesList();
  }

  public CitySorter() {
    this.sortingAlgorithm = null;
    this.cities = null;
  }

  public void performSort() {
    if (sortingAlgorithm != null && cities != null) {
      sortingAlgorithm.sort(cities);
    }
  }

  public void setSortingAlgorithm(SortingAlgorithm sortingAlgorithm) {
    this.sortingAlgorithm = sortingAlgorithm;
  }
}
