package datastore_test;
import java.util.List;

import user.Source;

public class TestSource implements Source {
    private final List<Integer> input;
    
    public TestSource(List<Integer> input) {
        this.input = input;
    }
    
    @Override
    public String getPath() {
        return "test-input";
    }
    
    public List<Integer> getInput(){
        return input;
    }
}
