package compute.eng;

import datastore.StorageComputeEngAPI;

public class ComputationAPIImplementation implements ComputationAPI{
    private final StorageComputeEngAPI storageAPI;
    public ComputationAPIImplementation(StorageComputeEngAPI storageAPI) {
        this.storageAPI = storageAPI;
    }
    public ComputationResult computeResult(ComputationRequest computeRequest) {
        return null;
    }
    
}
