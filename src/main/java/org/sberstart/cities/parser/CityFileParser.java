package org.sberstart.cities.parser;

import org.sberstart.cities.CityRepository;
import org.sberstart.cities.exceptions.BadCitiesFileNameException;
import org.sberstart.cities.exceptions.BadCityLineInputException;
import org.sberstart.cities.model.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class CityFileParser {
    private static final int CITY_FIELDS_COUNT = 6;
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String REGION = "region";
    private static final String DISTRICT = "district";
    private static final String POPULATION = "population";
    private static final String FOUNDATION = "foundation";

    private final CityRepository repository;

    public CityFileParser(CityRepository repository) {
        this.repository = repository;
    }

    private static void checkIfFieldEmpty(String field, String fieldName) {
        if (field == null || field.isEmpty()) {
            throw new IllegalArgumentException("Field " + fieldName + " cannot be empty");
        }
    }

    private static int extractCityIntegerField(String field, String fieldName) {
        checkIfFieldEmpty(field, fieldName);

        String message = "Bad city " + fieldName + ". Expected integer number, but has: " + field;
        if (field.matches(".*[^ .\\-0-9].*")) {
            throw new IllegalArgumentException(message);
        }
        if (field.matches("^-.*")) {
            throw new IllegalArgumentException("Field " + fieldName + " cannot be negative");
        }
        int ret;
        try {
            ret = Integer.parseInt(field.replaceAll("[ .\\-]", ""));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(message, e);
        }
        return ret;
    }

    private static String extractCityStringField(String field, String fieldName) {
        checkIfFieldEmpty(field, fieldName);
        if (!field.matches("[ .\\-0-9а-яА-Яa-zA-Z]*")) {
            throw new IllegalArgumentException("Bad field " + fieldName + ": " + field);
        }

        return field;
    }

    private static City createCityObjectFromLine(String line) {
        if (line.isEmpty()) {
            return null;
        }

        String[] elements = line.split(";");

        if (elements.length != CITY_FIELDS_COUNT) {
            throw new IllegalArgumentException("Bad elements count");
        }

        extractCityIntegerField(elements[0], ID);

        return new City(extractCityStringField(elements[1], NAME),
                extractCityStringField(elements[2], REGION),
                extractCityStringField(elements[3], DISTRICT),
                extractCityIntegerField(elements[4], POPULATION),
                extractCityIntegerField(elements[5], FOUNDATION));
    }

    public void parseObjectsFromInputFile(String filename) {
        URL resource;

        try {
            resource = getClass().getClassLoader().getResource(filename);
        } catch (NullPointerException e) {
            throw new BadCitiesFileNameException(filename, e);
        }

        if (resource == null) {
            throw new BadCitiesFileNameException(filename);
        }

        try (Scanner scanner = new Scanner(new File(resource.getFile()))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                repository.add(createCityObjectFromLine(line));
            }
        } catch (FileNotFoundException e) {
            throw new BadCitiesFileNameException(filename, e);
        } catch (IllegalArgumentException e) {
            throw new BadCityLineInputException(e.getMessage(), e);
        }
    }
}
