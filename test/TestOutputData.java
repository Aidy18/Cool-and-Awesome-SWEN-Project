import java.util.List;
import datastore.OutputData;

public class TestOutputData implements OutputData{
    private List<String> data;
    
    public TestOutputData(List<String> data) {
        this.data = data;
    }
    
    public List<String> getTokens(){
        return this.data;
    }
    
    public String getTokenAt(int index) {
        return data.get(index);
    }
    
    public void write(String out) {
        data.add(out);
    }
}
