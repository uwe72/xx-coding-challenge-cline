package de.exxcellent.challenge.analysis;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for FootballRecord domain model.
 */
class FootballRecordTest {

    @Test
    void fromMapValid() {
        Map<String, String> map = new HashMap<>();
        map.put("team", "Alpha");
        map.put("goals", "10");
        map.put("goals allowed", "8");
        FootballRecord record = FootballRecord.fromMap(map);
        assertEquals("Alpha", record.getTeam());
        assertEquals(2, record.getSpread());
    }

    @Test
    void fromMapMissingTeam() {
        Map<String, String> map = new HashMap<>();
        map.put("goals", "5");
        map.put("goals allowed", "3");
        assertThrows(IllegalArgumentException.class, () -> FootballRecord.fromMap(map));
    }

    @Test
    void fromMapInvalidNumber() {
        Map<String, String> map = new HashMap<>();
        map.put("team", "Beta");
        map.put("goals", "x");
        map.put("goals allowed", "1");
        assertThrows(NumberFormatException.class, () -> FootballRecord.fromMap(map));
    }
}
