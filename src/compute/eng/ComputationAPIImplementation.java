package compute.eng;

import datastore.IntData;
import datastore.StorageComputeEngAPI;

public class ComputationAPIImplementation implements ComputationAPI {
    private final StorageComputeEngAPI storageAPI;

    public ComputationAPIImplementation(StorageComputeEngAPI storageAPI) {
        this.storageAPI = storageAPI;
    }

    @Override
    public ComputationResult computeResult(ComputationRequest computeRequest) {
        Integer n = computeRequest.getDataValue();
        Integer result = getGreatestPrimeFactor(n);
        return new ComputationResult() {
            @Override
            public Integer getGreatestPrimeFactor() {
                return result;
            }
        };
    }

    private Integer getGreatestPrimeFactor(Integer n) {
        Integer largestPrime = -1;
        
        // No prime factors exist for 0 and 1
        if (n < 2) {
           return largestPrime; 
        }
        
        // Any even factors are taken out
        while (n % 2 == 0) {
            largestPrime = 2;
            n /= 2;
        }

        // Using sqrt(n) as the limit guarantees no repeating calculations
        for (int i = 3; i * i <= n; i += 2) {
            // Divide down by odd factors
            while (n % i == 0) {
                largestPrime = i;
                n /= i;
            }
        }

        // A remainder > 1 means n itself is prime
        if (n > 1) {
            largestPrime = n;
        }

        return largestPrime;
    }
}