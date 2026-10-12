package user;

import project.annotations.NetworkAPIPrototype;

public class UserComputeEngPrototype {
    @NetworkAPIPrototype
    public void prototypeClient(UserComputeEngAPI api) {
        // 1. specify the source + destination
        Source inputSource = new Source() {
            @Override
            public String getPath() {
                return "foo.txt";
            }
        };

        Destination outputDestination = new Destination() {
            @Override
            public String getPath() {
                return "bar.txt";
            }
        };

        // 2. create the request with ',' as the default delimiter
        UserComputeRequest request = new UserComputeRequest() {
            public Source getSource() {
                return inputSource;
            }

            public Destination getDestination() {
                return outputDestination;
            }

            public String getDelimiter() {
                return ",";
            }

            public boolean useDefaultDelimiter() {
                return true;
            }
        };

        // 3 output the work submitted to the handler
        UserComputeResponse submitted = api.compute(request);
        System.out.println("Job submitted: " + submitted);
    }
}
