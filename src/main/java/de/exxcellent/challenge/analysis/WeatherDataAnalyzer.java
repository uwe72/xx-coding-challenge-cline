package de.exxcellent.challenge.analysis;

import de.exxcellent.challenge.csv.CsvFileReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Analyzer for weather data: finds the day with the smallest temperature spread.
 */
public class WeatherDataAnalyzer implements DataAnalyzer {

    @Override
    public String findMinSpread(String filePath) throws IOException {
        List<Map<String, String>> records = CsvFileReader.readAsMap(filePath);
        String minDay = null;
        long minSpread = Long.MAX_VALUE;
        for (Map<String, String> record : records) {
                String maxStr = record.get("mxt");
                String minStr = record.get("mnt");
                if (maxStr == null || minStr == null) {
                    throw new NullPointerException("Missing required column");
                }
                long maxTemp = Long.parseLong(maxStr);
                long minTemp = Long.parseLong(minStr);
                long spread = Math.abs(maxTemp - minTemp);
                if (spread < minSpread) {
                    minSpread = spread;
                    minDay = record.get("day");
                }
            }
        return minDay;
    }
}
