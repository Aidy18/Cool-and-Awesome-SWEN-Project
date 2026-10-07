package datastore;

import project.annotations.ProcessAPI;
import compute.eng.ComputationResult;

@ProcessAPI
public interface StorageComputeEngAPI {
    IntData read(StoreRequest storeRequest);
    void write(StoreRequest storeRequest, ComputationResult result);
}
