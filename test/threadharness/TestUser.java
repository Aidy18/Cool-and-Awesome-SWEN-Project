package threadharness;

import java.io.File;

import user.Destination;
import user.Source;
import user.UserComputeEngAPI;
import user.UserComputeRequest;

public class TestUser {
    private final UserComputeEngAPI coordinator;

    public TestUser(UserComputeEngAPI coordinator) {
        this.coordinator = coordinator;
    }

    public void run(String outputPath) {
        // NOTE: delimiter was changed to reflect
        // the proper file format
        char delimiter = ',';
        String inputPath = "test" + File.separatorChar + "threadharness" + File.separatorChar + "testInputFile.test";

        // Call the appropriate method(s) on the coordinator to get it to
        // run the compute job specified by inputPath, outputPath, and delimiter

        Source source = new Source() {
            @Override
            public String getPath() {
                return inputPath;
            }
        };

        Destination destination = new Destination() {
            @Override
            public String getPath() {
                return outputPath;
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
                return String.valueOf(delimiter);
            }

            @Override
            public boolean useDefaultDelimiter() {
                return false;
            }
        };
        coordinator.compute(request);
    }

}
