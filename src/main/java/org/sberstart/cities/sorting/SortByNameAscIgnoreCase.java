package org.sberstart.cities.sorting;

import org.sberstart.cities.model.City;

import java.util.Comparator;
import java.util.List;

public class SortByNameAscIgnoreCase implements SortingAlgorithm {
  public SortByNameAscIgnoreCase() {
  }

  @Override
  public void sort(List<City> cities) {
    cities.sort(new Comparator<City>() {
      @Override
      public int compare(City o1, City o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
      }
    });
  }
}
