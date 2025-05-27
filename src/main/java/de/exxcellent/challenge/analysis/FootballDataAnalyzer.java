package de.exxcellent.challenge.analysis;

import de.exxcellent.challenge.csv.CsvFileReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Analyzer for football data: finds the team with the smallest goal spread.
 */
public class FootballDataAnalyzer implements DataAnalyzer {

    @Override
    public String findMinSpread(String filePath) throws IOException {
        List<Map<String, String>> records = CsvFileReader.readAsMap(filePath);
        String minTeam = null;
        long minSpread = Long.MAX_VALUE;
        for (Map<String, String> record : records) {
            String goalsStr = record.get("goals");
            String goalsAllowedStr = record.get("goals allowed");
            if (goalsStr == null || goalsAllowedStr == null) {
                throw new NullPointerException("Missing required column");
            }
            long goals = Long.parseLong(goalsStr);
            long goalsAllowed = Long.parseLong(goalsAllowedStr);
            long spread = Math.abs(goals - goalsAllowed);
            if (spread < minSpread) {
                minSpread = spread;
                minTeam = record.get("team");
            }
        }
        return minTeam;
    }
}
