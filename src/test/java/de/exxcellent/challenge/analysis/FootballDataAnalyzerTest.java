package de.exxcellent.challenge.analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for FootballDataAnalyzer covering default and edge cases.
 */
class FootballDataAnalyzerTest {

    private final FootballDataAnalyzer analyzer = new FootballDataAnalyzer();

    @Test
    void testDefaultFootball() throws IOException {
        // Uses classpath resource football.csv
        String result = analyzer.findMinSpread("football.csv");
        assertEquals("Aston_Villa", result, "Expected Aston_Villa for smallest goal spread");
    }

    @Test
    void testSwappedColumns(@TempDir Path tempDir) throws IOException {
        // Header columns in different order
        Path file = tempDir.resolve("swapped.csv");
        String content = "Goals Allowed,Team,Goals\n" +
                         "10,Alpha,12\n" +
                         "5,Beta,6\n";
        Files.writeString(file, content);
        String result = analyzer.findMinSpread(file.toString());
        assertEquals("Beta", result, "Should handle swapped header order correctly");
    }

    @Test
    void testMissingColumn(@TempDir Path tempDir) throws IOException {
        // Missing required Goals or Goals Allowed columns
        Path file = tempDir.resolve("missing.csv");
        String content = "Team,Points\n" +
                         "Alpha,10\n" +
                         "Beta,8\n";
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
        // Non-numeric goal values
        Path file = tempDir.resolve("invalid.csv");
        String content = "Team,Goals,Goals Allowed\n" +
                         "Alpha,x,5\n";
        Files.writeString(file, content);
        assertThrows(NumberFormatException.class, () -> analyzer.findMinSpread(file.toString()),
                     "Invalid number format should cause NumberFormatException");
    }
}
