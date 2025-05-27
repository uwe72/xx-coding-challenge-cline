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
 * Unit tests for FootballDataAnalyzer using custom CSVs.
 */
class FootballDataAnalyzerTest {

    private final FootballDataAnalyzer analyzer = new FootballDataAnalyzer();

    @Test
    void testHappyPath(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("football.csv");
        String content = "team,goals,goals allowed\n" +
                         "Alpha,10,8\n" +
                         "Beta,6,4\n";
        Files.writeString(file, content);
        String result = analyzer.findMinSpread(file.toString());
        List<Map<String, String>> maps = new CsvDataReader().read(file.toString());
        String expected = maps.stream()
            .map(FootballRecord::fromMap)
            .min(Comparator.comparingLong(FootballRecord::getSpread))
            .map(FootballRecord::getTeam)
            .orElse(null);
        assertEquals(expected, result, "Should pick record with smallest spread");
    }

    @Test
    void testSwappedColumns(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("swapped.csv");
        String content = "goals allowed,team,goals\n" +
                         "10,Alpha,12\n" +
                         "5,Beta,6\n";
        Files.writeString(file, content);
        String result = analyzer.findMinSpread(file.toString());
        assertEquals("Beta", result, "Should handle swapped header order");
    }

    @Test
    void testMissingColumn(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("missing.csv");
        String content = "team,points\n" +
                         "Alpha,10\n" +
                         "Beta,8\n";
        Files.writeString(file, content);
        assertThrows(IllegalArgumentException.class, 
                     () -> analyzer.findMinSpread(file.toString()),
                     "Missing columns should throw IllegalArgumentException");
    }

    @Test
    void testEmptyFile(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("empty.csv");
        Files.writeString(file, "");
        String result = analyzer.findMinSpread(file.toString());
        assertNull(result, "Empty file should return null");
    }

    @Test
    void testInvalidNumber(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("invalid.csv");
        String content = "team,goals,goals allowed\n" +
                         "Alpha,x,5\n";
        Files.writeString(file, content);
        assertThrows(NumberFormatException.class, 
                     () -> analyzer.findMinSpread(file.toString()),
                     "Invalid number should throw NumberFormatException");
    }
}
