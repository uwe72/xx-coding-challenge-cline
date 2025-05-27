package de.exxcellent.challenge.analysis;

import de.exxcellent.challenge.csv.CsvFileReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Default DataReader implementation using CsvFileReader.
 * @author Uwe Clement <uwe.clement@gmail.com>
 */
public class CsvDataReader implements DataReader {

    @Override
    public List<Map<String, String>> read(String filePath) throws IOException {
        return CsvFileReader.readAsMap(filePath);
    }
}
