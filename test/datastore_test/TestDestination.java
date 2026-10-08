package datastore_test;

import java.util.List;

import user.Destination;

public class TestDestination implements Destination {
    private final List<String> output;

    public TestDestination(List<String> output) {
        this.output = output;
    }

    @Override
    public String getPath() {
        return "test-output";
    }

    public List<String> getOutput() {
        return output;
    }
}
