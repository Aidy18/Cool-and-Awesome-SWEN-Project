package user;

import java.util.List;
import java.util.Objects;

import compute.eng.ComputationAPI;
import compute.eng.ComputationRequest;
import compute.eng.ComputationResult;
import datastore.IntData;
import datastore.StorageComputeEngAPI;
import datastore.StoreRequest;

//demonstrates the communication flow of APIs
public class WorkHandler implements UserComputeEngAPI {
    private final StorageComputeEngAPI storeAPI;
    private final ComputationAPI computationAPI;

    public WorkHandler(StorageComputeEngAPI storeAPI, ComputationAPI computationAPI) {
        this.storeAPI = Objects.requireNonNull(storeAPI, "StorageAPI cannot be null");
        this.computationAPI = Objects.requireNonNull(computationAPI, "ComputationAPI cannot be null");
    }

    @Override
    public UserComputeResponse compute(UserComputeRequest request) {
        try {
            // Validate the request
            Objects.requireNonNull(request, "Request cannot be null");
            Source source = Objects.requireNonNull(request.getSource(), "Source cannot be null");
            Destination destination = Objects.requireNonNull(request.getDestination(), "Destination cannot be null");

            // Send a storage request to store from source to destination
            StoreRequest storeRequest = new StoreRequest() {
                @Override
                public Source getSource() {
                    return source;
                }

                @Override
                public Destination getDestination() {
                    return destination;
                }
            };

            // Read data from specified source & store in a data container
            IntData data = Objects.requireNonNull(storeAPI.read(storeRequest), "StorageAPI read null data");
            List<Integer> intValues = Objects.requireNonNull(data.getInts(), "Input values cannot be null");

            if (intValues.isEmpty()) {
                throw new IllegalArgumentException("Input data contains no values");
            }

            Integer lastResult = -1;

            for (Integer val : intValues) {
                Objects.requireNonNull(val, "Input value cannot be null");

                // Make a request for each data value
                ComputationRequest computationRequest = new ComputationRequest() {
                    @Override
                    public IntData getData() {
                        return data;
                    }

                    @Override
                    public Integer getDataValue() {
                        return val;
                    }
                };
                // Do the computation(s)
                ComputationResult result = Objects.requireNonNull(computationAPI.computeResult(computationRequest),
                        "ComputationAPI returned null");

                // Write the result out to the destination
                storeAPI.write(storeRequest, result);
                lastResult = result.getGreatestPrimeFactor();
            }

            // Return a response to the user indicating the last computed result
            String msg = "Computations successful";
            return new UserComputeResponse(lastResult, msg);

        } catch (Exception e) {
            String errorMsg = e.getMessage();
            if (errorMsg == null || errorMsg.isBlank()) {
                errorMsg = "An unexpected error occurred";
            }
            return new UserComputeResponse(e.getClass().getSimpleName() + ": " + errorMsg);
        }
    }
}
