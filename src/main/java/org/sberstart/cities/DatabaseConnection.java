package org.sberstart.cities;

import org.sberstart.cities.exceptions.ConnectionNotEstablishedException;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {

    private String propertiesPath;

    public DatabaseConnection(String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }

    private Properties extractProperties() {
        Properties properties = new Properties();
        try {
            properties.load(DatabaseConnection.class
                    .getClassLoader()
                    .getResourceAsStream(this.propertiesPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public Connection establishConnection() {
        try {
            Properties properties = extractProperties();
            return DriverManager.getConnection(properties.getProperty("url"), properties);
        } catch (SQLException e) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Throwable throwable : e) {
                stringBuilder.append(throwable.toString()); //todo
            }
            throw new ConnectionNotEstablishedException(stringBuilder.toString(), "Could't connect to database", e);
        } catch (NullPointerException e) {
            throw new ConnectionNotEstablishedException("Bad database properties file", e);
        }
    }
}
