package user;

public class UserComputeResponse {
    private final Integer gpf;
    private final String message;
    private final boolean success;

    public UserComputeResponse(Integer gpf, String message) {
        this.gpf = gpf;
        this.message = message;
        this.success = true;
    }
    
    // Failed computation
    public UserComputeResponse(String message) {
        this.gpf = null;
        this.message = message;
        this.success = false;
    }

    public Integer getGPF() {
        return gpf;
    }

    public String getMessage() {
        return message;
    }
    
    public boolean isSuccess() {
        return success;
    }
}
