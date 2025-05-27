package de.exxcellent.challenge.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple CSV file reader that skips header and splits on commas.
 * Supports reading from file system or classpath resources.
 * Provides record maps keyed by normalized header names for robust column handling.
 * @author Uwe Clement <uwe.clement@gmail.com>
 */
public class CsvFileReader {

    /**
     * Reads a CSV file, skipping the first line (header).
     *
     * @param filePath Path to the CSV file or resource file name.
     * @return List of String arrays, each array representing one record.
     * @throws IOException if file cannot be read.
     */
    public static List<String[]> read(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        BufferedReader reader = openReader(filePath);
        try (BufferedReader br = reader) {
            // skip header
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\s*,\\s*");
                records.add(parts);
            }
        }
        return records;
    }

    /**
     * Reads a CSV file into a list of record maps keyed by normalized header.
     *
     * @param filePath Path to the CSV file or resource file name.
     * @return List of maps where each map represents one record: normalized header -> value.
     * @throws IOException if file cannot be read.
     */
    public static List<Map<String, String>> readAsMap(String filePath) throws IOException {
        List<Map<String, String>> records = new ArrayList<>();
        BufferedReader reader = openReader(filePath);
        try (BufferedReader br = reader) {
            // read header line
            String headerLine = br.readLine();
            if (headerLine == null) {
                return records;
            }
            String[] rawHeaders = headerLine.split("\\s*,\\s*");
            List<String> headers = new ArrayList<>();
            for (String header : rawHeaders) {
                headers.add(header.trim().toLowerCase());
            }

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\s*,\\s*");
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < headers.size() && i < parts.length; i++) {
                    map.put(headers.get(i), parts[i]);
                }
                records.add(map);
            }
        }
        return records;
    }

    private static BufferedReader openReader(String filePath) throws IOException {
        try {
            return new BufferedReader(new FileReader(filePath));
        } catch (IOException e) {
            InputStream is = CsvFileReader.class.getResourceAsStream("/de/exxcellent/challenge/" + filePath);
            if (is == null) {
                throw e;
            }
            return new BufferedReader(new InputStreamReader(is));
        }
    }
}
