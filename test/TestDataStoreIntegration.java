import datastore.StorageComputeEngAPI;
import datastore.StoreRequest;
import compute.eng.ComputationResult;
import datastore.IntData;
import datastore.OutputData;

public class TestDataStoreIntegration implements StorageComputeEngAPI {
    private IntData in;
    private OutputData out;
    
    public TestDataStoreIntegration(IntData in, OutputData out) {
        this.in = in;
        this.out = out;
    }
    
    public IntData read(StoreRequest storeRequest) {
        for(Integer num : in.getInts()) {
            System.out.println("Read: " + num);
        }
        return in;
    }
    
    public void write(StoreRequest storeRequest, ComputationResult result) {
        for(Integer num : in.getInts()) {
            doSomething(num);
            out.write("Processed " + num + " to " + storeRequest.getDestination().getPath());
        }
    }
    private void doSomething(Integer num) {
        // I lied! This does NOTHING!!
    }
}
