package user;

import project.annotations.NetworkAPI;

@NetworkAPI
public interface UserComputeEngAPI {
    UserComputeResponse compute(UserComputeRequest computeRequest);
}
