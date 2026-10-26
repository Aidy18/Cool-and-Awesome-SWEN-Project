package user;

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
        this.storeAPI = storeAPI;
        this.computationAPI = computationAPI;
    }

    // 1. Take in the user request & validate
    @Override
    public UserComputeResponse compute(UserComputeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (request.getSource() == null) {
            throw new IllegalArgumentException("Please provide a valid source");
        }
        if (request.getDestination() == null) {
            throw new IllegalArgumentException("Please provide a valid destination");
        }
        // 2. Send a storage request to store from source to destination
        StoreRequest storeRequest = new StoreRequest() {
            @Override
            public Source getSource() {
                return request.getSource();
            }

            @Override
            public Destination getDestination() {
                return request.getDestination();
            }
        };

        // 3. Read data from specified source & store in a data container
        IntData data = storeAPI.read(storeRequest);

        if (data == null) {
            throw new IllegalArgumentException("Data not found");
        }

        // 4. Send a request to compute the data
        ComputationRequest computationRequest = new ComputationRequest() {
            @Override
            public IntData getData() {
                return data;
            }
        };

        // 5. Compute the data
        ComputationResult result = computationAPI.computeResult(computationRequest);

        // 6. Write the result out to the destination
        storeAPI.write(storeRequest, result);

        // 7. Return a response to the user indicating success or fail
        String msg = "";
        return new UserComputeResponse(result.getGPF(), msg);
    }
}
