package threadharness;

import java.io.File;

import user.Destination;
import user.Source;
import user.UserComputeEngAPI;
import user.UserComputeRequest;

public class TestUser {

    // TODO 3: change the type of this variable to the name you're using for your
    // @NetworkAPI interface; also update the parameter passed to the constructor
    private final UserComputeEngAPI coordinator;

    public TestUser(UserComputeEngAPI coordinator) {
        this.coordinator = coordinator;
    }

    public void run(String outputPath) {
        // note: delimiter was changed to reflect 
        // my file format of choice
        char delimiter = ',';
        String inputPath = "test" + File.separatorChar + "threadharness" + File.separatorChar + "testInputFile.test";

        // TODO 4: Call the appropriate method(s) on the coordinator to get it to
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
