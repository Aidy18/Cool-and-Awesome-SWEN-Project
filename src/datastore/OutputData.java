package datastore;

import java.util.List;

public interface OutputData {
    public List<String> getTokens();
    public String getTokenAt(int index);
    public void write(String out);
}
