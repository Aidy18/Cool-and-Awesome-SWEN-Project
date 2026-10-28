package project;

import user.Destination;
import user.Source;
import user.UserComputeEngAPI;
import user.UserComputeRequest;
import user.UserComputeResponse;
import user.WorkHandler;
import compute.eng.ComputationAPI;
import compute.eng.ComputationRequest;
import compute.eng.ComputationResult;
import datastore.IntData;
import datastore.StorageComputeEngAPI;
import datastore.StoreRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

public class TestUserComputeEngAPI {
    @Test
    public void testUserAPI() {
        ComputationAPI mockCompAPI = Mockito.mock(ComputationAPI.class);
        StorageComputeEngAPI mockStoreAPI = Mockito.mock(StorageComputeEngAPI.class);

        UserComputeEngAPI testAPI = new WorkHandler(mockStoreAPI, mockCompAPI);

        UserComputeRequest mockRequest = Mockito.mock(UserComputeRequest.class);

        // Provide a valid source and destination.
        Source source = Mockito.mock(Source.class);
        when(source.getPath()).thenReturn("in.txt");
        Destination destination = Mockito.mock(Destination.class);
        when(destination.getPath()).thenReturn("out.txt");

        UserComputeRequest request = Mockito.mock(UserComputeRequest.class);

        when(request.getSource()).thenReturn(source);
        when(request.getDestination()).thenReturn(destination);

        // Configure the storage API to return input data.
        IntData mockData = Mockito.mock(IntData.class);

        when(mockStoreAPI.read(any(StoreRequest.class))).thenReturn(mockData);

        when(mockData.getInts()).thenReturn(Arrays.asList(25));

        when(mockData.getIntAt(Mockito.anyInt())).thenReturn(25);

        // Configure the computation API to return true.
        ComputationResult computationResult = Mockito.mock(ComputationResult.class);

        when(computationResult.getGreatestPrimeFactor()).thenReturn(5);

        when(mockCompAPI.computeResult(any(ComputationRequest.class))).thenReturn(computationResult);

        // Instantiate the coordination component.
        UserComputeEngAPI userComputeAPI = new WorkHandler(mockStoreAPI, mockCompAPI);

        // Execute the computation.
        UserComputeResponse response = userComputeAPI.compute(request);

        // Verify the result and component interactions.
        Assertions.assertNotNull(response);
        assertEquals(response.getGPF(), 5);

        verify(mockStoreAPI).read(any(StoreRequest.class));
        verify(mockCompAPI).computeResult(any(ComputationRequest.class));
        verify(mockStoreAPI).write(any(StoreRequest.class), eq(computationResult));
    }
}
