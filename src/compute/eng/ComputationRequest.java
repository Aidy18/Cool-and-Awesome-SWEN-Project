package compute.eng;

import datastore.IntData;

public interface ComputationRequest {
    // Note: this stub is to be phased out, as it does not properly reflect
    // a request for a single computation
    IntData getData();
    
    Integer getDataValue();
}
