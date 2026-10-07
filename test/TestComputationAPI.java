import compute.eng.ComputationAPIImplementation;
import compute.eng.ComputationRequest;
import datastore.StorageComputeEngAPI;
import datastore.IntData;
import datastore.DataVal;
import compute.eng.ComputationResult;
import java.math.BigInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class TestComputationAPI {
    
    @Test
    public void testComputation() throws Exception {
        DataVal mockDataVal = Mockito.mock(DataVal.class);
        when(mockDataVal.getValue()).thenReturn(BigInteger.valueOf(6));
        IntData mockIntData = Mockito.mock(IntData.class);
        when(mockIntData.getInts()).thenReturn(mockDataVal);
        ComputationRequest mockRequest = Mockito.mock(ComputationRequest.class);
        when(mockRequest.getData()).thenReturn(mockIntData);
        
        StorageComputeEngAPI mockStorageAPI = Mockito.mock(StorageComputeEngAPI.class);
        
        ComputationAPIImplementation testAPI = new ComputationAPIImplementation(mockStorageAPI);
        Assertions.assertEquals(testAPI.computeResult(mockRequest), new ComputationResult() {
            @Override
            public boolean isGPF() {
                return false;
            }
        });
    }
}