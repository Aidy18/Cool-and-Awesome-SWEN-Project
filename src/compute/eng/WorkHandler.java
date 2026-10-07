package compute.eng;

import datastore.IntData;
import datastore.StorageComputeEngAPI;
import datastore.StoreRequest;
import user.Destination;
import user.Source;
import user.UserComputeEngAPI;
import user.UserComputeRequest;
import user.UserComputeResponse;

//demonstrates the communication flow of APIs
public class WorkHandler implements UserComputeEngAPI {
    private final StorageComputeEngAPI storeAPI;
    private final ComputationAPI computationAPI;

    public WorkHandler(StorageComputeEngAPI storeAPI, ComputationAPI computationAPI) {
        this.storeAPI = storeAPI;
        this.computationAPI = computationAPI;
    }
}
