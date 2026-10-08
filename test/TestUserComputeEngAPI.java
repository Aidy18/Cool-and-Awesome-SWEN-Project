import user.UserComputeEngAPIImplementation;
import user.UserComputeRequest;
import user.UserComputeResponse;
import compute.eng.ComputationAPI;

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
        UserComputeEngAPIImplementation testAPI = new UserComputeEngAPIImplementation(mockCompAPI);

        UserComputeRequest mockRequest = Mockito.mock(UserComputeRequest.class);

        Assertions.assertEquals(testAPI.compute(mockRequest).isGPF(), false);
    }
}
