package user;

public interface ComputeRequest {
    Source getSource();
    Destination getDestination();
    String getDelimiter();
    boolean useDefaultDelimiter();
}
