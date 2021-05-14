package org.sberstart.cities.sorting;

import org.sberstart.cities.model.City;

import java.util.Comparator;
import java.util.List;

public class SortByDistrictAndNameAscNotIgnoreCase implements SortingAlgorithm {
  public SortByDistrictAndNameAscNotIgnoreCase() {
  }

  @Override
  public void sort(List<City> cities) {
    cities.sort(Comparator.comparing(City::getDistrict).thenComparing(City::getName));
  }
}
