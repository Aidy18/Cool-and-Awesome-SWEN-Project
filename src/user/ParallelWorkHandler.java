package user;

import java.util.Objects;

import compute.eng.ComputationAPI;
import datastore.StorageComputeEngAPI;

public class ParallelWorkHandler implements UserComputeEngAPI{
    private final StorageComputeEngAPI storeAPI;
    private final ComputationAPI computationAPI;

    public ParallelWorkHandler(StorageComputeEngAPI storeAPI, ComputationAPI computationAPI) {
        this.storeAPI = Objects.requireNonNull(storeAPI, "StorageAPI cannot be null");
        this.computationAPI = Objects.requireNonNull(computationAPI, "ComputationAPI cannot be null");
    }
    
    @Override
    public UserComputeResponse compute(UserComputeRequest request) {
        return NetworkingLogicCore.compute(request, storeAPI, computationAPI, true);
    }
}
