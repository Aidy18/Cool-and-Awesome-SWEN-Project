package compute.eng;

import project.annotations.ConceptualAPI;

@ConceptualAPI
public interface ComputationAPI {
    ComputationResult computeResult(ComputationRequest computeRequest);
}
