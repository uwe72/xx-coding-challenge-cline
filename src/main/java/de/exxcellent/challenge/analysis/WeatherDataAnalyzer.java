package de.exxcellent.challenge.analysis;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import de.exxcellent.challenge.csv.CsvFileReader;

/**
 * Analyzer for weather data: finds the day with the smallest temperature spread.
 * @author Uwe Clement <uwe.clement@gmail.com>
 */
public class WeatherDataAnalyzer implements DataAnalyzer {
    private static final Logger LOGGER = Logger.getLogger(WeatherDataAnalyzer.class.getName());
    private static final String KEY_DAY = "day";
    private static final String KEY_MAX = "mxt";
    private static final String KEY_MIN = "mnt";

    @Override
    public String findMinSpread(String filePath) throws IOException {
        List<Map<String, String>> records = CsvFileReader.readAsMap(filePath);
        return records.stream()
            .map(record -> {
                String day = record.get(KEY_DAY);
                if (day == null) {
                    throw new IllegalArgumentException("Missing required column: " + KEY_DAY);
                }
                String maxStr = record.get(KEY_MAX);
                if (maxStr == null) {
                    throw new IllegalArgumentException("Missing required column: " + KEY_MAX);
                }
                String minStr = record.get(KEY_MIN);
                if (minStr == null) {
                    throw new IllegalArgumentException("Missing required column: " + KEY_MIN);
                }
                try {
                    long max = Long.parseLong(maxStr);
                    long min = Long.parseLong(minStr);
                    return new AbstractMap.SimpleEntry<>(day, Math.abs(max - min));
                } catch (NumberFormatException e) {
                    LOGGER.warning("Invalid number format in weather record: " + record + " - " + e.getMessage());
                    throw e;
                }
            })
            .min(Comparator.comparingLong(AbstractMap.SimpleEntry::getValue))
            .map(AbstractMap.SimpleEntry::getKey)
            .orElse(null);
    }
}
