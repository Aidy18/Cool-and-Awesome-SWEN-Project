package project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import compute.eng.ComputationAPI;
import compute.eng.ComputationAPIImplementation;
import compute.eng.ComputationResult;
import datastore.IntData;
import datastore.StorageComputeEngAPI;
import datastore.StoreRequest;
import user.Destination;
import user.Source;
import user.UserComputeRequest;
import user.UserComputeResponse;
import user.WorkHandler;

public class UserComputeExceptionIntegrationTest {
    @Test
    public void testStorageExceptionBecomesErrorResponse() {
        // Simulate a storage failure.
        String msg = "Storage failure detected";
        StorageComputeEngAPI failedStorage = new StorageComputeEngAPI() {
            @Override
            public IntData read(StoreRequest request) {
                throw new IllegalStateException(msg);
            }

            @Override
            public void write(StoreRequest request, ComputationResult result) {
                // We don't care about writing for this test
            }
        };

        ComputationAPI computationAPI = new ComputationAPIImplementation();

        WorkHandler coordinator = new WorkHandler(failedStorage, computationAPI);

        Source source = new Source() {
            @Override
            public String getPath() {
                return "input.txt";
            }
        };

        Destination destination = new Destination() {
            @Override
            public String getPath() {
                return "output.txt";
            }
        };

        UserComputeRequest request = new UserComputeRequest() {
            @Override
            public Source getSource() {
                return source;
            }

            @Override
            public Destination getDestination() {
                return destination;
            }

            @Override
            public String getDelimiter() {
                return ",";
            }

            @Override
            public boolean useDefaultDelimiter() {
                return true;
            }
        };

        // Exceptions should be chained up to become a response message
        UserComputeResponse response = coordinator.compute(request);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(null, response.getGPF());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertTrue(response.getMessage().contains(msg));
    }
}
