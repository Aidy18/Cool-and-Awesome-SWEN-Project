package project;


import user.UserComputeEngAPI;
import user.UserComputeRequest;
import user.UserComputeResponse;
import user.WorkHandler;
import compute.eng.ComputationAPI;
import datastore.StorageComputeEngAPI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class TestUserComputeEngAPI {
    @Test
    public void testUserAPI() {
        ComputationAPI mockCompAPI = Mockito.mock(ComputationAPI.class);
        StorageComputeEngAPI mockStoreAPI = Mockito.mock(StorageComputeEngAPI.class);

        UserComputeEngAPI testAPI = new WorkHandler(mockStoreAPI, mockCompAPI);

        UserComputeRequest mockRequest = Mockito.mock(UserComputeRequest.class);

        Assertions.assertEquals(testAPI.compute(mockRequest).getGPF(), 1);
    }
}
