package de.exxcellent.challenge.analysis;

/**
 * Factory for creating DataAnalyzer instances based on mode.
 *
 * @author Uwe Clement <uwe.clement@gmail.com>
 */
public class AnalyzerFactory {

    /**
     * Creates a DataAnalyzer for the given mode.
     *
     * @param mode 'weather' or 'football'
     * @return DataAnalyzer implementation
     */
    public static DataAnalyzer create(String mode) {
        if ("football".equalsIgnoreCase(mode)) {
            return new FootballDataAnalyzer();
        } else {
            return new WeatherDataAnalyzer();
        }
    }
}
