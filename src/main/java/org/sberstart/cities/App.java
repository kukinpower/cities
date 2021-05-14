package org.sberstart.cities;

import org.sberstart.cities.service.CityService;
import org.sberstart.cities.sorting.SortByDistrictAndNameAscNotIgnoreCase;
import org.sberstart.cities.sorting.SortByNameAscIgnoreCase;

public class App {
    private static final String FILENAME = "cities.txt";
    private final CityRepository repository;
    private final CityService service;

    public App() {
        this.repository = new CityRepository();
        this.service = new CityService();
    }

    private void showRepoContentParsedFromFile() {
        service.fillRepository(FILENAME);
        System.out.println("--------- SIMPLE PRINT ---------");
        service.printAllCities();

        System.out.println("--------- SORT BY NAME IGNORE CASE ---------");
        service.setSort(new SortByNameAscIgnoreCase());
        service.sort();
        service.printAllCities();

        System.out.println("--------- SORT BY DISTRICT AND NAME NOT IGNORING CASE ---------");
        service.setSort(new SortByDistrictAndNameAscNotIgnoreCase());
        service.sort();
        service.printAllCities();

        System.out.println("--------- GET CITY POSITION WITH MAX POPULATION ---------");
        System.out.println(service.getCityPositionWithMaxPopulation());

        System.out.println("--------- GET CITIES PER REGION TABLE ---------");
        System.out.println(service.countCitiesPerRegion());
    }

    private void showDatabaseOutput() {
        service.fillDatabase();
        service.printAllFromDatabase();
    }

    public void run() {
        
        try {
            showRepoContentParsedFromFile();
            showDatabaseOutput();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
