package project.checkpointtests;

import compute.eng.ComputationAPI;
import compute.eng.ComputationAPIImplementation;
import datastore.StorageComputeEngAPI;
import datastore.StorageComputeEngAPIImplementation;
import user.Destination;
import user.Source;
import user.UserComputeEngAPI;
import user.UserComputeRequest;
import user.UserComputeResponse;
import user.WorkHandler;

public class ManualTestingFramework {

    public static final String INPUT = "manualTestInput.txt";
    public static final String OUTPUT = "manualTestOutput.txt";

    public static void main(String[] args) {
        // 1:
        // Instantiate a real (ie, class definition lives in the src/ folder)
        // implementation
        // of all 3 APIs
        ComputationAPI computationAPI = new ComputationAPIImplementation();
        StorageComputeEngAPI storageAPI = new StorageComputeEngAPIImplementation();
        UserComputeEngAPI userAPI = new WorkHandler(storageAPI, computationAPI);

        // 2:
        // Run a computation with an input file of <root project
        // dir>/manualTestInput.txt
        // and an output of <root project dir>/manualTestOutput.txt, with a delimiter of
        // ','
        UserComputeRequest request = new UserComputeRequest() {

            @Override
            public Source getSource() {
                return () -> INPUT;
            }

            @Override
            public Destination getDestination() {
                return () -> OUTPUT;
            }

            @Override
            public String getDelimiter() {
                return ",";
            }

            @Override
            public boolean useDefaultDelimiter() {
                return false;
            }
        };
        
        UserComputeResponse response = userAPI.compute(request);
        System.out.println("Result: " + response.getGPF());
        System.out.println("Output written to: " + OUTPUT);
    }
}
