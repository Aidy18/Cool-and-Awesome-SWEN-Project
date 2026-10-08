import java.util.List;
import datastore.IntData;

public class TestIntData implements IntData{
    private List<Integer> data;
    
    public TestIntData(List<Integer> data) {
        this.data = data;
    }
    
    public List<Integer> getInts() {
        return this.data;
    }
    
    public Integer getIntAt(int index) {
        return data.get(index);
    }
    
    public void add(Integer num) {
        data.add(num);
    }
}
