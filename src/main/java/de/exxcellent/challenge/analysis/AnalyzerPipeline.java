package de.exxcellent.challenge.analysis;

import java.io.IOException;

/**
 * Pipeline combining a DataReader and a DataAnalyzer to process a CSV file.
 * @author Uwe Clement <uwe.clement@gmail.com>
 */
public class AnalyzerPipeline implements Pipeline<String, String> {

    private final DataAnalyzer analyzer;

    /**
     * Constructs a pipeline with the given analyzer.
     *
     * @param analyzer data analyzer
     */
    public AnalyzerPipeline(DataAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    @Override
    public String run(String filePath) throws IOException {
        return analyzer.findMinSpread(filePath);
    }
}
