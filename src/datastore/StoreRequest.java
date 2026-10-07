package datastore;
import user.Source;
import user.Destination;

//requests storage from the specified source to the destination
public interface StoreRequest {
    Source getSource();
    Destination getDestination();
}
