package de.exxcellent.challenge.analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Comparator;

/**
 * Unit tests for AnalyzerPipeline using temporary CSV files.
 */
class AnalyzerPipelineTest {

    @Test
    void testFootballPipeline(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("football.csv");
        String csv = "team,goals,goals allowed\n" +
                     "Alpha,10,8\n" +
                     "Beta,6,4\n";
        Files.writeString(file, csv);

        Pipeline<String, String> pipeline =
            new AnalyzerPipeline(new FootballDataAnalyzer());
        String result = pipeline.run(file.toString());

        // Compute expected minimum spread dynamically
        List<Map<String, String>> maps = new CsvDataReader().read(file.toString());
        String expected = maps.stream()
            .map(FootballRecord::fromMap)
            .min(Comparator.comparingLong(FootballRecord::getSpread))
            .map(FootballRecord::getTeam)
            .orElse(null);
        assertEquals(expected, result, "Pipeline should match programmatically computed result");
    }

    @Test
    void testWeatherPipeline(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("weather.csv");
        String csv = "day,mxt,mnt\n" +
                     "1,30,20\n" +
                     "2,25,20\n";
        Files.writeString(file, csv);

        Pipeline<String, String> pipeline =
            new AnalyzerPipeline(new WeatherDataAnalyzer());
        String result = pipeline.run(file.toString());

        // Compute expected minimum spread dynamically
        List<Map<String, String>> maps = new CsvDataReader().read(file.toString());
        long minSpread = maps.stream()
            .mapToLong(m -> Math.abs(
                Long.parseLong(m.get("mxt")) - Long.parseLong(m.get("mnt"))
            ))
            .min()
            .orElse(Long.MAX_VALUE);
        String expected = maps.stream()
            .filter(m -> Math.abs(
                Long.parseLong(m.get("mxt")) - Long.parseLong(m.get("mnt"))
            ) == minSpread)
            .map(m -> m.get("day"))
            .findFirst()
            .orElse(null);
        assertEquals(expected, result, "Pipeline should match programmatically computed result");
    }
}
