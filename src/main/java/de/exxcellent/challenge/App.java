package de.exxcellent.challenge;

import java.util.logging.Logger;
import java.util.logging.Level;

import de.exxcellent.challenge.analysis.DataAnalyzer;
import de.exxcellent.challenge.analysis.Pipeline;
import de.exxcellent.challenge.analysis.AnalyzerPipeline;
import de.exxcellent.challenge.analysis.AnalyzerFactory;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Uwe Clement <uwe.clement@gmail.com>
 */
public final class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

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
            Pipeline<String, String> pipeline = new AnalyzerPipeline(analyzer);
            String result = pipeline.run(filePath);
            if ("football".equals(mode)) {
                LOGGER.info(String.format("Team with smallest goal spread       : %s", result));
            } else {
                LOGGER.info(String.format("Day with smallest temperature spread : %s", result));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing data: " + e.getMessage());
            System.exit(1);
        }
    }
}
