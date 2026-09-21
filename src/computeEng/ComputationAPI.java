package computeEng;

import project.annotations.ConceptualAPI;

@ConceptualAPI
public interface ComputationAPI {
    Result computeResult(ComputeRequest computeRequest);
}
