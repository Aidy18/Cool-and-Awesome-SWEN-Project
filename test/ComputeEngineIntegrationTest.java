import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import compute.eng.ComputationAPI;
import compute.eng.ComputationAPIImplementation;

public class ComputeEngineIntegrationTest {
    @Test
    public void testComputeEng() {
        List<Integer> input = Arrays.asList(1, 10, 15);
        
        List<String> output = new ArrayList<>();
        
        DataStoreIntegrationTest storageAPI = new DataStoreIntegrationTest();
        
        ComputationAPI computationAPI = new ComputationAPIImplementation();
        
        UserComputeEng userAPI = new 
    }
}
