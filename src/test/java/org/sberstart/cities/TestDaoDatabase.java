package org.sberstart.cities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.sberstart.cities.exceptions.ConnectionNotEstablishedException;
import org.sberstart.cities.model.City;
import org.sberstart.cities.service.CityService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestDaoDatabase {
    private final static String PROPS = "db.properties";
    @Test
    public void testAddCity() throws SQLException {
        DAO dao = new DAO(new DatabaseConnection(PROPS).establishConnection());
        City city = new City("Москва", "Московская область", "Центральный", 12_655_050, 1147);
        dao.createTable();
        dao.addNewCityToDatabase(city);
        List<City> cities = Collections.singletonList(city);

        assertEquals(cities, dao.getAllCities());

    }

    @Test
    public void testConnectionNotEstablishedException() {
        assertThrows(ConnectionNotEstablishedException.class, () -> new DatabaseConnection("bad path").establishConnection());
    }

    @Test
    public void testGetAllCitiesFromDatabase() {
    }
}
