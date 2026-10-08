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
        IntData mockIntData = Mockito.mock(IntData.class);
        when(mockIntData.getInts()).thenReturn(new ArrayList<>(Arrays.asList(5)));
        when(mockIntData.getIntAt(anyInt())).thenReturn(5);
        ComputationRequest mockRequest = Mockito.mock(ComputationRequest.class);
        when(mockRequest.getData()).thenReturn(mockIntData);

        StorageComputeEngAPI mockStorageAPI = Mockito.mock(StorageComputeEngAPI.class);

        ComputationAPIImplementation testAPI = new ComputationAPIImplementation(mockStorageAPI);
        Assertions.assertEquals(testAPI.computeResult(mockRequest).isGPF(), false);
    }
}