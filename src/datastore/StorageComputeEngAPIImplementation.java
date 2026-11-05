package datastore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        Objects.requireNonNull(storeRequest, "Storage request cannot be null");
        Source source = Objects.requireNonNull(storeRequest.getSource(), "Data source cannot be null");
        String inputPathString = Objects.requireNonNull(source.getPath(), "Input path cannot be null");

        if (inputPathString.isBlank()) {
            throw new IllegalArgumentException("Source path cannot be blank");
        }

        Path inputPath = Path.of(inputPathString);

        if (!Files.isRegularFile(inputPath)) {
            throw new IllegalArgumentException("Input file is not regular");
        }

        if (!Files.isReadable(inputPath)) {
            throw new IllegalArgumentException("Input file is not readable");
        }

        try {
            List<String> lines = Files.readAllLines(inputPath);
            List<Integer> numbers = new ArrayList<>();

            for (String line : lines) {
                String trimmed = line.trim();

                if (!trimmed.isEmpty()) {
                    // Handling the default delimiter only for now
                    String[] values = trimmed.split(",");

                    for (String value : values) {
                        String trimmedValue = value.trim();

                        if (trimmedValue.isEmpty()) {
                            throw new IllegalArgumentException("Input file contains an empty value");
                        }
                        try {
                            numbers.add(Integer.parseInt(trimmedValue));
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid data in input file: " + trimmedValue, e);
                        }
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
        Objects.requireNonNull(storeRequest, "Storage request cannot be null");
        Objects.requireNonNull(result, "Computation result cannot be null");

        Destination destination = Objects.requireNonNull(storeRequest.getDestination(),
                "Data destination cannot be null");
        String destinationPathString = Objects.requireNonNull(destination.getPath(), "Output file path cannot be null");

        if (destinationPathString.isBlank()) {
            throw new IllegalArgumentException("Output file path cannot be blank");
        }

        Path outputPath = Path.of(destinationPathString);

        // Check the parent directory if there is one
        Path parent = outputPath.toAbsolutePath().getParent();

        // Validate the parent exists, as the output file may not yet exist
        if (parent != null && !Files.isDirectory(parent)) {
            throw new IllegalArgumentException("Output directory does not exist: " + parent);
        }

        String parsedResult;

        if (result.getGreatestPrimeFactor() < 2) {
            parsedResult = "none";
        } else {
            parsedResult = Integer.toString(result.getGreatestPrimeFactor());
        }

        outputResults.add(parsedResult);

        // Write all to one comma-separated line
        String output = String.join(",", outputResults);
        try {
            Files.writeString(outputPath, output);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to write output file: " + outputPath, exception);
        }
    }
}
