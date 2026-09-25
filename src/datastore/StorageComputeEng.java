package datastore;

import project.annotations.ProcessAPI;

@ProcessAPI
public interface StorageComputeEng {
    IntData read(StoreRequest storeRequest);
    void write(StoreRequest storeRequest, IntData data);
}
