package de.exxcellent.challenge.analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for WeatherDataAnalyzer covering default and edge cases.
 */
class WeatherDataAnalyzerTest {

    private final WeatherDataAnalyzer analyzer = new WeatherDataAnalyzer();

    @Test
    void testDefaultWeather() throws IOException {
        // Uses classpath resource weather.csv
        String result = analyzer.findMinSpread("weather.csv");
        assertEquals("14", result, "Expected day 14 for smallest temperature spread");
    }

    @Test
    void testSwappedColumns(@TempDir Path tempDir) throws IOException {
        // Header columns in different order
        Path file = tempDir.resolve("swapped.csv");
        String content = "MnT,MxT,Day\n" +
                         "10,20,1\n" +
                         "5,8,2\n";
        Files.writeString(file, content);
        String result = analyzer.findMinSpread(file.toString());
        assertEquals("2", result, "Should handle swapped header order correctly");
    }

    @Test
    void testMissingColumn(@TempDir Path tempDir) throws IOException {
        // Missing required MxT or MnT columns
        Path file = tempDir.resolve("missing.csv");
        String content = "Day,AvT\n1,70\n2,60\n";
        Files.writeString(file, content);
        assertThrows(NullPointerException.class, () -> analyzer.findMinSpread(file.toString()),
                     "Missing columns should cause NullPointerException");
    }

    @Test
    void testEmptyFile(@TempDir Path tempDir) throws IOException {
        // Empty file should return null
        Path file = tempDir.resolve("empty.csv");
        Files.writeString(file, "");
        String result = analyzer.findMinSpread(file.toString());
        assertNull(result, "Empty file should return null result");
    }

    @Test
    void testInvalidNumbers(@TempDir Path tempDir) throws IOException {
        // Non-numeric temperature values
        Path file = tempDir.resolve("invalid.csv");
        String content = "Day,MxT,MnT\n1,a,5\n";
        Files.writeString(file, content);
        assertThrows(NumberFormatException.class, () -> analyzer.findMinSpread(file.toString()),
                     "Invalid number format should cause NumberFormatException");
    }
}
