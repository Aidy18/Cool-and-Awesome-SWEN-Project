package project;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import datastore.StorageComputeEngAPI;
import datastore.StoreRequest;
import compute.eng.ComputationResult;
import datastore.IntData;

public class InMemoryDataStore implements StorageComputeEngAPI {

    @Override
    public IntData read(StoreRequest request) {
        // Test to read some input
        TestSource source = (TestSource) request.getSource();
        List<Integer> input = source.getInput();
        
        if(input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input not found");
        }

        return new IntData() {
            @Override
            public List<Integer> getInts() {
                return new ArrayList<Integer>(input);
            }

            @Override
            public Integer getIntAt(int index) {
                return input.get(index);
            }
        };
    }

    @Override
    public void write(StoreRequest request, ComputationResult result) {
        // Quick test to write some output
        TestDestination destination = (TestDestination) request.getDestination();
        
        // Verify edge cases
        String finalValue;
        if(result.getGreatestPrimeFactor() < 2) {
            finalValue = "none";
        }
        else {
            finalValue = Integer.toString(result.getGreatestPrimeFactor());
        }
        destination.getOutput().add(finalValue);
    }
}