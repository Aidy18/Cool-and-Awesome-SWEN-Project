package compute.eng;

import project.annotations.ConceptualAPI;

@ConceptualAPI
public interface ComputationAPI {
    Result computeResult(ComputeRequest computeRequest);
}
