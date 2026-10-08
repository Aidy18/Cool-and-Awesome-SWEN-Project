package user;

public class UserComputeResponse {
    private final Integer gpf;
    private final String message;

    public UserComputeResponse(Integer gpf, String message) {
        this.gpf = gpf;
        this.message = message;
    }

    public Integer getGPF() {
        return gpf;
    }

    public String getMessage() {
        return message;
    }
}
