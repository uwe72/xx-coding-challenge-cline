package de.exxcellent.challenge.analysis;

import java.io.IOException;

/**
 * Generic pipeline interface for processing input to produce output.
 *
 * @param <I> input type
 * @param <O> output type
 * @author Uwe Clement <uwe.clement@gmail.com>
 */
public interface Pipeline<I, O> {
    /**
     * Execute the pipeline with the given input.
     *
     * @param input pipeline input
     * @return pipeline output
     * @throws IOException if processing fails
     */
    O run(I input) throws IOException;
}
