package datastore;

import project.annotations.ProcessAPI;

@ProcessAPI
public interface StorageComputeEngAPI {
    IntData read(StoreRequest storeRequest);
    void write(StoreRequest storeRequest, IntData data);
}
