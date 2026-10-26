package compute.eng;

import datastore.IntData;
import datastore.StorageComputeEngAPI;

public class ComputationAPIImplementation implements ComputationAPI {
    private final StorageComputeEngAPI storageAPI;

    public ComputationAPIImplementation(StorageComputeEngAPI storageAPI) {
        this.storageAPI = storageAPI;
    }
    
    @Override
    public ComputationResult computeResult(ComputationRequest computeRequest) {
        IntData data = computeRequest.getData();
        
        return null;
    }

}
