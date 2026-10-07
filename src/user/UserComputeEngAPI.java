package user;

import project.annotations.NetworkAPI;

@NetworkAPI
public interface UserComputeEngAPI {
    void compute(ComputeRequest computeRequest);
}
