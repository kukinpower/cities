package org.sberstart.cities;

import java.time.LocalDateTime;

public class CityLogger {
    public static void log(String message) {
        System.err.println(LocalDateTime.now() + " " + message);
    }
}
