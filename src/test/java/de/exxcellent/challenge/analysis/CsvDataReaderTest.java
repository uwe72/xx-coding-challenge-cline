package de.exxcellent.challenge.analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CsvDataReader.
 */
class CsvDataReaderTest {

    private final CsvDataReader reader = new CsvDataReader();

    @Test
    void testReadClasspathResource() throws IOException {
        // football.csv has header plus data; first record must contain team "Arsenal"
        List<Map<String, String>> records = reader.read("football.csv");
        assertFalse(records.isEmpty(), "Should read at least one record");
        Map<String, String> first = records.get(0);
        assertTrue(first.containsKey("team"), "Record should contain 'team' column");
        assertEquals("Arsenal", first.get("team"), "First record team should be Arsenal");
    }

    @Test
    void testReadTempFile(@TempDir Path tempDir) throws IOException {
        // create a small CSV with header and two rows
        Path file = tempDir.resolve("sample.csv");
        String csv = "colA,colB\nx,1\ny,2\n";
        Files.writeString(file, csv);
        List<Map<String, String>> records = reader.read(file.toString());
        assertEquals(2, records.size(), "Should read two records");
        assertEquals("x", records.get(0).get("cola"));
        assertEquals("2", records.get(1).get("colb"));
    }

    @Test
    void testMissingFile() {
        assertThrows(IOException.class, () -> reader.read("does-not-exist.csv"));
    }
}
