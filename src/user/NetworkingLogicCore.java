package user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import compute.eng.ComputationAPI;
import compute.eng.ComputationRequest;
import compute.eng.ComputationResult;
import datastore.IntData;
import datastore.StorageComputeEngAPI;
import datastore.StoreRequest;

final class NetworkingLogicCore {
    static final int MAX_THREADS = 4;

    public NetworkingLogicCore() {

    }

    static UserComputeResponse compute(UserComputeRequest request, StorageComputeEngAPI storageAPI,
            ComputationAPI computationAPI, boolean isParallel) {

        ExecutorService executor = null;

        try {
            Objects.requireNonNull(request, "Request cannot be null");

            Source source = Objects.requireNonNull(request.getSource(), "Source cannot be null");

            Destination destination = Objects.requireNonNull(request.getDestination(), "Destination cannot be null");

            Objects.requireNonNull(storageAPI, "Storage API cannot be null");
            Objects.requireNonNull(computationAPI, "Computation API cannot be null");

            StoreRequest storageRequest = new StoreRequest() {

                @Override
                public Destination getDestination() {
                    return destination;
                }

                @Override
                public Source getSource() {
                    return source;
                }
            };

            // Read the inputs
            IntData data = Objects.requireNonNull(storageAPI.read(storageRequest), "Input data cannot be read");

            List<Integer> values = Objects.requireNonNull(data.getInts(), "Input values cannot be null");

            if (values.isEmpty()) {
                throw new IllegalArgumentException("Input data contains no values");
            }

            // Validate inputs before proceeding
            for (Integer value : values) {
                Objects.requireNonNull(value, "Input value cannot be null");
                Objects.requireNonNull(value, "Integer cannot be null");
            }

            List<ComputationResult> results = new ArrayList<>();

            if (isParallel) {

                // Create the thread pool
                executor = Executors.newFixedThreadPool(MAX_THREADS);

                List<Future<ComputationResult>> futures = new ArrayList<>();

                // Submit computation requests in order
                for (Integer value : values) {
                    ComputationRequest computationRequest = createComputationRequest(values, value);
                    Future<ComputationResult> future = executor
                            .submit(() -> computationAPI.computeResult(computationRequest));
                    futures.add(future);
                }

                // Get the results in order of inputs
                for (Future<ComputationResult> future : futures) {
                    results.add(Objects.requireNonNull(future.get(), "Computation returned null"));
                }
            } else {
                // Single-threaded execution
                for (Integer value : values) {
                    ComputationRequest computationRequest = createComputationRequest(values, value);
                    ComputationResult result = computationAPI.computeResult(computationRequest);
                    results.add(Objects.requireNonNull(result, "Computation returned null"));
                }
            }

            // Write the results in order
            Integer lastResult = -1;

            for (ComputationResult result : results) {

                boolean writeSuccessful = storageAPI.write(storageRequest, result);

                if (!writeSuccessful) {
                    throw new IllegalStateException("Write operation failed");
                }

                lastResult = result.getGreatestPrimeFactor();
            }
            // indicate the operation succeeded
            String msg = "Computation success";
            return new UserComputeResponse(lastResult, msg);

        } catch (InterruptedException exception) {

            Thread.currentThread().interrupt();

            return new UserComputeResponse("An interruption has occurred");

        } catch (Exception exception) {
            String message = exception.getMessage();

            if (message == null || message.isBlank()) {
                message = "An unexpected error occurred";
            }

            return new UserComputeResponse(exception.getClass().getSimpleName() + ": " + message);
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
        }
    }

    private static ComputationRequest createComputationRequest(List<Integer> data, Integer value) {
        IntData singleValueData = new IntData() {
            @Override
            public List<Integer> getInts() {
                return data;
            }

            @Override
            public Integer getIntAt(int i) {
                return data.get(i);
            }
        };

        return new ComputationRequest() {
            @Override
            public IntData getData() {
                return singleValueData;
            }

            @Override
            public Integer getDataValue() {
                return value;
            }
        };
    }
}
