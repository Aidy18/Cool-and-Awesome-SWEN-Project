package compute.eng;

import project.annotations.ConceptualAPIPrototype;
import datastore.IntData;

import java.util.List;

public class ComputationPrototype {
    @ConceptualAPIPrototype
    public void prototypeClient(ComputationAPI api) {
        // 1. Make a request to compute & grab the data values
        ComputationRequest request = new ComputationRequest() {
            @Override
            public IntData getData() {
                return new IntData() {
                    @Override
                    public List<Integer> getInts() {
                        return null;
                    }

                    @Override
                    public Integer getIntAt(int index) {
                        return 0;
                    }
                };
            }
        };
        // 2. Compute the result with the data and output
        ComputationResult result = api.computeResult(request);
        System.out.println("Result: " + result);
    }
}
