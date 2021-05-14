package org.sberstart.cities.exceptions;

public class BadCitiesFileNameException extends IllegalArgumentException {
    public BadCitiesFileNameException(String filename) {
        super("File <" + filename + "> not found");
    }

    public BadCitiesFileNameException(String filename, Throwable cause) {
        super("File <" + filename + "> not found", cause);
    }
}
