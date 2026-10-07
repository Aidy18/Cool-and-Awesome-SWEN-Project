import datastore.StorageComputeEngAPI;
import datastore.StorageComputeEngAPIImplementation;
import datastore.StoreRequest;
import compute.eng.ComputationAPI;
import user.Source;
import user.Destination;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

public class TestStorageComputeEngAPI {
    @Test
    public void testStorageAPI() {
        ComputationAPI mockCompAPI = Mockito.mock(ComputationAPI.class);
        StorageComputeEngAPIImplementation testAPI = new StorageComputeEngAPIImplementation(mockCompAPI);
        
        StoreRequest mockRequest = Mockito.mock(StoreRequest.class);
        when(mockRequest.getSource()).thenReturn()
    }
}
