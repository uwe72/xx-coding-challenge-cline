package de.exxcellent.challenge.analysis;

/**
 * Custom exception for data processing errors, such as missing columns.
 * @author Uwe Clement <uwe.clement@gmail.com>
 */
public class DataProcessingException extends IllegalArgumentException {
    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
