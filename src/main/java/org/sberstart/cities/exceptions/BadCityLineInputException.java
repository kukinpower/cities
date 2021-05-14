package org.sberstart.cities.exceptions;

public class BadCityLineInputException extends IllegalArgumentException {
    public BadCityLineInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
