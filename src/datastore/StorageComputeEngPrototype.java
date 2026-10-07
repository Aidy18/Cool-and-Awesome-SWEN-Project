package datastore;

import project.annotations.ProcessAPIPrototype;
import user.Source;
import user.Destination;
import compute.eng.ComputationResult;

public class StorageComputeEngPrototype {
    @ProcessAPIPrototype
    public void prototypeClient(StorageComputeEngAPI api) {
        // 1. Get the source + destination, then make a request
        Source source = new Source() {
            @Override
            public String getPath() {
                return "foo.txt";
            }
        };

        Destination destination = new Destination() {
            @Override
            public String getPath() {
                return "bar.txt";
            }
        };

        StoreRequest storageRequest = new StoreRequest() {

            @Override
            public Source getSource() {
                return source;
            }

            @Override
            public Destination getDestination() {
                return destination;
            }
        };

        // 2. read the data from source
        IntData data = api.read(storageRequest);

        System.out.println("Data read: " + data);

        ComputationResult result = new ComputationResult() {

            @Override
            public boolean isGPF() {
                return false;
            }
        };

        // 3. write out the data to destination, then output
        api.write(storageRequest, result);
        System.out.println("Data written: " + result);
    }
}
