package de.exxcellent.challenge.analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for WeatherDataAnalyzer using custom CSVs.
 */
class WeatherDataAnalyzerTest {

    private final WeatherDataAnalyzer analyzer = new WeatherDataAnalyzer();

    @Test
    void testHappyPath(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("weather.csv");
        String csv = "day,mxt,mnt\n" +
                     "1,30,20\n" +
                     "2,25,20\n" +
                     "3,26,22\n";
        Files.writeString(file, csv);
        String result = analyzer.findMinSpread(file.toString());
        assertEquals("3", result, "Should pick day 3 for smallest spread");
    }

    @Test
    void testSwappedColumns(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("swapped.csv");
        String csv = "mnt,mxt,day\n" +
                     "20,30,1\n" +
                     "8,10,2\n";
        Files.writeString(file, csv);
        String result = analyzer.findMinSpread(file.toString());
        assertEquals("2", result, "Should handle swapped header order");
    }

    @Test
    void testMissingColumn(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("missing.csv");
        String csv = "day,avt\n" +
                     "1,70\n" +
                     "2,60\n";
        Files.writeString(file, csv);
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
        String csv = "day,mxt,mnt\n" +
                     "1,a,5\n";
        Files.writeString(file, csv);
        assertThrows(NumberFormatException.class,
                     () -> analyzer.findMinSpread(file.toString()),
                     "Invalid number should throw NumberFormatException");
    }
}
