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
        return NetworkingLogicCore.compute(request, storeAPI, computationAPI, false);
    }
}
