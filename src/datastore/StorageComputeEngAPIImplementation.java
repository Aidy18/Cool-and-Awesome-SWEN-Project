package datastore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import compute.eng.ComputationAPI;
import compute.eng.ComputationResult;
import user.Destination;
import user.Source;

public class StorageComputeEngAPIImplementation implements StorageComputeEngAPI {
    private final ComputationAPI compAPI;
    // private final List<String> outputResults = new ArrayList<>();
    private final ConcurrentHashMap<Path, List<String>> outputResults = new ConcurrentHashMap<>();

    public StorageComputeEngAPIImplementation(ComputationAPI compAPI) {
        this.compAPI = compAPI;
    }

    public StorageComputeEngAPIImplementation() {
        this.compAPI = null;
    }

    @Override
    public IntData read(StoreRequest storeRequest) {
        try {
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
        } catch (Exception e) {
            // a temporary solution for now
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean write(StoreRequest storeRequest, ComputationResult result) {
        try {
            Objects.requireNonNull(storeRequest, "Storage request cannot be null");
            Objects.requireNonNull(result, "Computation result cannot be null");

            Destination destination = Objects.requireNonNull(storeRequest.getDestination(),
                    "Data destination cannot be null");
            String destinationPathString = Objects.requireNonNull(destination.getPath(),
                    "Output file path cannot be null");

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

            List<String> results = outputResults.computeIfAbsent(outputPath, ignored -> new ArrayList<>());
            synchronized (results) {
                List<String> parsedResults = new ArrayList<>(results);
                String parsedResult;

                if (result.getGreatestPrimeFactor() < 2) {
                    parsedResult = "none";
                } else {
                    parsedResult = Integer.toString(result.getGreatestPrimeFactor());
                }

                parsedResults.add(parsedResult);

                String output = String.join(",", parsedResults);
                Files.writeString(outputPath, output);

                results.clear();
                results.addAll(parsedResults);
                return true;
            }

            // Write all to one comma-separated line
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            return false;
        }
    }
}
