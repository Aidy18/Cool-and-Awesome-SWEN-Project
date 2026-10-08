package datastore;

import java.util.List;

public interface IntData {
    List<Integer> getInts();
    Integer getIntAt(int index);
    public void add(Integer i);
}
