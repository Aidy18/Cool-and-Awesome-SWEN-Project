package user;

public class UserComputeResponse {
    private final boolean gpf;
    private final String message;
    
    public UserComputeResponse(boolean gpf, String message) {
        this.gpf = gpf;
        this.message = message;
    }
    
    public boolean isGPF() {
        return gpf;
    }
    
    public String getMessage() {
        return message;
    }
}
