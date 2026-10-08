import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import datastore.StorageComputeEngAPI;
import datastore.StoreRequest;
import compute.eng.ComputationResult;
import datastore.IntData;

public class DataStoreIntegrationTest implements StorageComputeEngAPI {

    @Override
    public IntData read(StoreRequest request) {

        TestSource source = (TestSource) request.getSource();

        List<Integer> input = source.getInput();

        return new IntData() {
            @Override
            public List<Integer> getInts() {
                return new ArrayList<Integer>(Arrays.asList(0));
            }

            @Override
            public Integer getIntAt(int index) {
                return getInts().get(0);
            }
        };
    }

    @Override
    public void write(StoreRequest request, ComputationResult result) {

        TestDestination destination = (TestDestination) request.getDestination();

        destination.getOutput().add(Boolean.toString(result.isGPF()));
    }
}