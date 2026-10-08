import datastore.StorageComputeEngAPI;
import datastore.StorageComputeEngAPIImplementation;
import datastore.StoreRequest;
import compute.eng.ComputationAPI;
import user.Source;
import user.Destination;
import datastore.IntData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

public class TestStorageComputeEngAPI {
    @Test
    public void testStorageAPI() {
        ComputationAPI mockCompAPI = Mockito.mock(ComputationAPI.class);
        StorageComputeEngAPIImplementation testAPI = new StorageComputeEngAPIImplementation(mockCompAPI);

        Source mockSource = Mockito.mock(Source.class);
        when(mockSource.getPath()).thenReturn("foo.txt");
        StoreRequest mockRequest = Mockito.mock(StoreRequest.class);
        when(mockRequest.getSource()).thenReturn(mockSource);

        Assertions.assertEquals(testAPI.read(mockRequest).getIntAt(0), 6);
    }
}
