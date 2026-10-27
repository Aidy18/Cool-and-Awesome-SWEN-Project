package datastore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import compute.eng.ComputationAPI;
import compute.eng.ComputationResult;
import user.Destination;
import user.Source;

public class StorageComputeEngAPIImplementation implements StorageComputeEngAPI {
    private final ComputationAPI compAPI;
    private final List<String> outputResults = new ArrayList<>();

    public StorageComputeEngAPIImplementation(ComputationAPI compAPI) {
        this.compAPI = compAPI;
    }

    @Override
    public IntData read(StoreRequest storeRequest) {
        Source source = storeRequest.getSource();
        Path inputPath = Path.of(source.getPath());

        try {
            List<String> lines = Files.readAllLines(inputPath);
            List<Integer> numbers = new ArrayList<>();

            for (String line : lines) {
                String trimmedLine = line.trim();

                if (!trimmedLine.isEmpty()) {
                    String[] values = trimmedLine.split(",");

                    for (String value : values) {
                        numbers.add(Integer.parseInt(value.trim()));
                    }
                }
            }

            if (numbers.isEmpty()) {
                throw new IllegalArgumentException("Input file contains no integers");
            }

            outputResults.clear();

            return new IntData() {
                @Override
                public List<Integer> getInts() {
                    return numbers;
                }

                @Override
                public Integer getIntAt(int index) {
                    return numbers.get(index);
                }
            };

        } catch (IOException exception) {
            throw new RuntimeException("Failed to read input file: " + inputPath, exception);
        }
    }

    @Override
    public void write(StoreRequest storeRequest, ComputationResult result) {
        Destination destination = storeRequest.getDestination();

        outputResults.add(Integer.toString(result.getGreatestPrimeFactor()));

        // Write all results as a single comma-separated line.
        String output = String.join(",", outputResults);

        try {
            Files.writeString(Path.of(destination.getPath()), output);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to write output file", exception);
        }
    }
}
