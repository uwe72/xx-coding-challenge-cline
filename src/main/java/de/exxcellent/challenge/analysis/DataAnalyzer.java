package de.exxcellent.challenge.analysis;

import java.io.IOException;

/**
 * Interface for finding the entry with the minimum spread in a CSV file.
 *
 * @author Uwe Clement <uwe.clement@gmail.com>
 */
public interface DataAnalyzer {
    /**
     * Reads the given CSV file and returns the key (e.g., day or team)
     * associated with the minimum spread value.
     *
     * @param filePath path to the CSV file
     * @return the identifier (day number or team name) with minimum spread
     * @throws IOException if an I/O error occurs
     */
    String findMinSpread(String filePath) throws IOException;
}
