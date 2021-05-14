package org.sberstart.cities.service;

import org.sberstart.cities.CityRepository;
import org.sberstart.cities.DAO;
import org.sberstart.cities.DatabaseConnection;
import org.sberstart.cities.model.City;
import org.sberstart.cities.parser.CityFileParser;
import org.sberstart.cities.sorting.CitySorter;
import org.sberstart.cities.sorting.SortByNameAscIgnoreCase;
import org.sberstart.cities.sorting.SortingAlgorithm;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class CityService {
    private final CitySorter sorter;
    private final CityRepository cityRepository;
    private final DatabaseConnection dataBaseConnection;
    private final DAO dao;
    private static final String properties = "db.properties";

    public CityService() {
        this.cityRepository = new CityRepository();
        this.sorter = new CitySorter(new SortByNameAscIgnoreCase(), this.cityRepository);
        this.dataBaseConnection = new DatabaseConnection(properties);
        this.dao = new DAO(dataBaseConnection.establishConnection());
    }

    public void setSort(SortingAlgorithm sortingAlgorithm) {
        sorter.setSortingAlgorithm(sortingAlgorithm);
    }

    public void sort() {
        sorter.performSort();
    }

    public void printAllCities() {
        for (City city : cityRepository.getCitiesList()) {
            System.out.println(city);
        }
    }

    public String getCityPositionWithMaxPopulation() {
        City[] cities = cityRepository.getCitiesList().toArray(new City[0]);

        int max = 0;
        int pos = 0;
        for (int i = 0; i < cities.length; i++) {
            if (cities[i].getPopulation() > max) {
                max = cities[i].getPopulation();
                pos = i;
            }
        }

        return "[" + pos + "]" + "=" + max;

    }

    public CityRepository getRepository() {
        return cityRepository;
    }

    public String countCitiesPerRegion() {
        Map<String, Long> map = cityRepository.getCitiesList()
                .stream()
                .collect(Collectors.groupingBy(City::getRegion, Collectors.counting()));

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey()).append(" - ").append(entry.getValue()).append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }

    public void fillRepository(String filename) {
        CityFileParser parser = new CityFileParser(cityRepository);
        parser.parseObjectsFromInputFile(filename);
    }

    public void fillDatabase() {
        try {
            dao.createTable();
            dao.addListOfCitiesToDatabase(cityRepository.getCitiesList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAllFromDatabase() {
        try {
            for (City city : dao.getAllCities()) {
                System.out.println(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
