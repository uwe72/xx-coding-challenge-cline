package de.exxcellent.challenge.analysis;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Abstraction for reading CSV data as a list of record maps.
 * @author Uwe Clement <uwe.clement@gmail.com>
 */
public interface DataReader {
    /**
     * Reads data from the given CSV file or resource.
     *
     * @param filePath path or resource name of the CSV
     * @return list of record maps keyed by normalized header
     * @throws IOException if reading fails
     */
    List<Map<String, String>> read(String filePath) throws IOException;
}
