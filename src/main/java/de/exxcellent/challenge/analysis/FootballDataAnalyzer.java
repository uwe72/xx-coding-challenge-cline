package de.exxcellent.challenge.analysis;

import de.exxcellent.challenge.csv.CsvFileReader;
import java.util.AbstractMap;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Analyzer for football data: finds the team with the smallest goal spread.
 * @author Uwe Clement <uwe.clement@gmail.com>
 */
public class FootballDataAnalyzer implements DataAnalyzer {

    @Override
    public String findMinSpread(String filePath) throws IOException {
        List<Map<String, String>> records = CsvFileReader.readAsMap(filePath);
        return records.stream()
                .map(FootballRecord::fromMap)
                .map(r -> new AbstractMap.SimpleEntry<>(r.getTeam(), r.getSpread()))
                .min(Comparator.comparingLong(AbstractMap.SimpleEntry::getValue))
                .map(AbstractMap.SimpleEntry::getKey)
                .orElse(null);
    }
}
