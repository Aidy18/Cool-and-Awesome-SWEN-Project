package user;

import compute.eng.ComputationAPI;

public class UserComputeEngAPIImplementation implements UserComputeEngAPI {
    private final ComputationAPI computeAPI;

    public UserComputeEngAPIImplementation(ComputationAPI computeAPI) {
        this.computeAPI = computeAPI;
    }

    @Override
    public UserComputeResponse compute(UserComputeRequest computeRequest) {
        return null;
    }
}
