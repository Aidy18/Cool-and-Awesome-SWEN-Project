package project;

import compute.eng.ComputationAPIImplementation;
import compute.eng.ComputationRequest;
import datastore.StorageComputeEngAPI;
import datastore.IntData;
import compute.eng.ComputationResult;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;

public class TestComputationAPI {

    @Test
    public void testComputation() throws Exception {
        // Mock the request with mocked IntData
        IntData mockIntData = Mockito.mock(IntData.class);
        when(mockIntData.getInts()).thenReturn(new ArrayList<>(Arrays.asList(25)));
        when(mockIntData.getIntAt(anyInt())).thenReturn(25);

        ComputationRequest mockRequest = Mockito.mock(ComputationRequest.class);
        when(mockRequest.getData()).thenReturn(mockIntData);
        when(mockRequest.getDataValue()).thenReturn(25);

        StorageComputeEngAPI storageAPI = Mockito.mock(StorageComputeEngAPI.class);

        // Test the computation with the mocked object
        ComputationAPIImplementation testAPI = new ComputationAPIImplementation(storageAPI);

        Assertions.assertEquals(testAPI.computeResult(mockRequest).getGreatestPrimeFactor(), 5);
    }

    // Additional test for already prime numbers
    @Test
    public void testPrime() throws Exception {
        IntData mockIntData = Mockito.mock(IntData.class);
        when(mockIntData.getInts()).thenReturn(new ArrayList<>(Arrays.asList(7)));
        when(mockIntData.getIntAt(anyInt())).thenReturn(7);

        ComputationRequest mockRequest = Mockito.mock(ComputationRequest.class);
        when(mockRequest.getData()).thenReturn(mockIntData);
        when(mockRequest.getDataValue()).thenReturn(7);

        StorageComputeEngAPI storageAPI = Mockito.mock(StorageComputeEngAPI.class);

        ComputationAPIImplementation testAPI = new ComputationAPIImplementation(storageAPI);

        Assertions.assertEquals(testAPI.computeResult(mockRequest).getGreatestPrimeFactor(), 7);
    }
}