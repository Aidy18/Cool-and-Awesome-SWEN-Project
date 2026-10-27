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
    
    public StorageComputeEngAPIImplementation() {
        this.compAPI = null;
    }

    @Override
    public IntData read(StoreRequest storeRequest) {
        Source source = storeRequest.getSource();
        Path inputPath = Path.of(source.getPath());

        try {
            List<String> lines = Files.readAllLines(inputPath);
            List<Integer> numbers = new ArrayList<>();

            for (String line : lines) {
                String trimmed = line.trim();

                if (!trimmed.isEmpty()) {
                    // Handling the default delimiter only for now
                    String[] values = trimmed.split(",");

                    for (String value : values) {
                        numbers.add(Integer.parseInt(value.trim()));
                    }
                }
            }

            if (numbers.isEmpty()) {
                throw new IllegalArgumentException("Input file contains no integers");
            }

            // Flush after every run
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
        String parsedResult;
        if(result.getGreatestPrimeFactor() < 2) {
            parsedResult = "none";
        }
        else {
            parsedResult = Integer.toString(result.getGreatestPrimeFactor());
        }
        outputResults.add(parsedResult);

        // Write all to one comma-separated line
        String output = String.join(",", outputResults);

        try {
            Files.writeString(Path.of(destination.getPath()), output);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to write output file", exception);
        }
    }
}
