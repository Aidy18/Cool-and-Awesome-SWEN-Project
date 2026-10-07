package user;

public interface UserComputeRequest {
    Source getSource();
    Destination getDestination();
    String getDelimiter();
    boolean useDefaultDelimiter();
}
