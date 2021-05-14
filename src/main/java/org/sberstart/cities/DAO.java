package org.sberstart.cities;

import org.sberstart.cities.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS Cities (" +
                    "    CityId int NOT NULL AUTO_INCREMENT primary key," +
                    "    Name varchar(255)," +
                    "    Region varchar(255)," +
                    "    District varchar(255)," +
                    "    Population int," +
                    "    Foundation int" +
                    ")";

    private static final String INSERT_CITY =
            "INSERT INTO Cities" +
                    " (Name, Region, District, Population, Foundation)" +
                    " values (?, ?, ?, ?, ?)";

    private static final String DELETE_CITY =
            "DELETE FROM emp WHERE" +
                    " name = ?" +
                    " and region = ?" +
                    " and district = ?" +
                    " and population = ?" +
                    " and foundation = ?;";

    private final Connection connection;
    private static final String GET_ALL_CITIES = "select * from Cities";

    public DAO(Connection connection) {
        this.connection = connection;
    }

    private int updateCity(City city, String operation) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(operation);

        preparedStatement.setString(1, city.getName());
        preparedStatement.setString(2, city.getRegion());
        preparedStatement.setString(3, city.getDistrict());
        preparedStatement.setInt(4, city.getPopulation());
        preparedStatement.setInt(5, city.getFoundation());

        return preparedStatement.executeUpdate();
    }

    public void addNewCityToDatabase(City city) throws SQLException {
        int affectedRows = updateCity(city, INSERT_CITY);
        CityLogger.log(affectedRows + " city added to database");
    }

    public void addListOfCitiesToDatabase(List<City> cities) throws SQLException {
        int i = 0;

        for (City city : cities) {
            addNewCityToDatabase(city);
            ++i;
        }

        CityLogger.log(i + " cities added to database");
    }

    public List<City> getAllCities() throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(GET_ALL_CITIES,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = callableStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();

        List<City> cities = new ArrayList<>();

        while (resultSet.next()) {
            List<Object> elements = new ArrayList<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                elements.add(resultSet.getObject(i));
            }
            cities.add(new City(
                    (String) elements.get(1),
                    (String) elements.get(2),
                    (String) elements.get(3),
                    (Integer) elements.get(4),
                    (Integer) elements.get(5)
            ));
        }
        return cities;
    }

    public void deleteCityFromDatabase(City city) throws SQLException {
        int affectedRows = updateCity(city, DELETE_CITY);
        System.out.println("City: " + city + " successfully deleted from database"); //todo log
    }

    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate(CREATE_TABLE);
    }
}
