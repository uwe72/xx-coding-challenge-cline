package de.exxcellent.challenge;

import de.exxcellent.challenge.analysis.DataAnalyzer;
import de.exxcellent.challenge.analysis.WeatherDataAnalyzer;
import de.exxcellent.challenge.analysis.FootballDataAnalyzer;
import de.exxcellent.challenge.analysis.AnalyzerFactory;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {
        String mode = "weather";
        String filePath;
        if (args.length >= 1 && "--football".equals(args[0])) {
            mode = "football";
            filePath = args.length >= 2 ? args[1] : "src/main/resources/de/exxcellent/challenge/football.csv";
        } else if (args.length >= 1 && "--weather".equals(args[0])) {
            filePath = args.length >= 2 ? args[1] : "src/main/resources/de/exxcellent/challenge/weather.csv";
        } else {
            filePath = "src/main/resources/de/exxcellent/challenge/weather.csv";
        }
        try {
            DataAnalyzer analyzer = AnalyzerFactory.create(mode);
            String result = analyzer.findMinSpread(filePath);
            if ("football".equals(mode)) {
                System.out.printf("Team with smallest goal spread       : %s%n", result);
            } else {
                System.out.printf("Day with smallest temperature spread : %s%n", result);
            }
        } catch (Exception e) {
            System.err.println("Error processing data: " + e.getMessage());
            System.exit(1);
        }
    }
}
