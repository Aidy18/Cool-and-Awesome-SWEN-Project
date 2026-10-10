package project;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import compute.eng.ComputationAPI;
import compute.eng.ComputationAPIImplementation;
import user.Destination;
import user.Source;
import user.UserComputeEngAPI;
import user.UserComputeRequest;
import user.UserComputeResponse;
import user.WorkHandler;

public class ComputeEngineIntegrationTest {
    @Test
    public void testComputeEng() {
        // Initialize input and output lists
        List<Integer> input = Arrays.asList(1, 10, 15);
        List<String> output = new ArrayList<>();

        // Instantiate the live implemented APIs
        DataStoreIntegrationTest storageAPI = new DataStoreIntegrationTest();
        ComputationAPI computationAPI = new ComputationAPIImplementation();
        UserComputeEngAPI userAPI = new WorkHandler(storageAPI, computationAPI);

        // Define source/destination to extract from the input/output lists
        TestSource source = new TestSource(input);
        TestDestination destination = new TestDestination(output);

        // Make the test request
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
                return null;
            }

            @Override
            public boolean useDefaultDelimiter() {
                return false;
            }
        };

        // Send a response
        UserComputeResponse response = userAPI.compute(request);

        // Check to ensure the Greatest Prime Factor is found
        // NOTE TO SELF: Edge cases like 1 will need to be dealt with
        List<String> expected = Arrays.asList("none", "5", "5");
        Assertions.assertEquals(output, expected);
    }
}
