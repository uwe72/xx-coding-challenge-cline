package de.exxcellent.challenge.analysis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AnalyzerFactory.
 */
class AnalyzerFactoryTest {

    @Test
    void testCreateFootball() {
        DataAnalyzer analyzer = AnalyzerFactory.create("football");
        assertTrue(analyzer instanceof FootballDataAnalyzer, "Factory should create FootballDataAnalyzer for 'football'");
    }

    @Test
    void testCreateWeatherDefault() {
        DataAnalyzer analyzer = AnalyzerFactory.create("weather");
        assertTrue(analyzer instanceof WeatherDataAnalyzer, "Factory should create WeatherDataAnalyzer for 'weather'");
    }

    @Test
    void testCreateCaseInsensitive() {
        DataAnalyzer analyzer = AnalyzerFactory.create("FOOTBALL");
        assertTrue(analyzer instanceof FootballDataAnalyzer, "Factory should be case-insensitive for 'FOOTBALL'");
    }

    @Test
    void testCreateUnknownDefaultsToWeather() {
        DataAnalyzer analyzer = AnalyzerFactory.create("unknown");
        assertTrue(analyzer instanceof WeatherDataAnalyzer, "Factory should default to WeatherDataAnalyzer");
    }
}
