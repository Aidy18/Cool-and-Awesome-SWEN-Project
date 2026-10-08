import datastore.StorageComputeEngAPI;

import datastore.StoreRequest;
import compute.eng.ComputationResult;
import datastore.IntData;

public class DataStoreIntegrationTest implements StorageComputeEngAPI {
    
    public IntData read(StoreRequest storeRequest) {
        
    }
    
    public void write(StoreRequest storeRequest, ComputationResult result) {
        
    }
    private void doSomething(Integer num) {
        // I lied! This does NOTHING!!
    }
}
