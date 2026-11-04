package project;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import compute.eng.ComputationAPIImplementation;
import compute.eng.ComputationRequest;
import datastore.IntData;

public class ComputationValidationTest {
    @Test
    public void testNull() {
        ComputationAPIImplementation testComputation = new ComputationAPIImplementation();
        assertThrows(NullPointerException.class, () -> testComputation.computeResult(null));
    }

    @Test
    public void testNegative() {
        ComputationAPIImplementation testComputation = new ComputationAPIImplementation();
        ComputationRequest mockRequest = Mockito.mock(ComputationRequest.class);
        when(mockRequest.getDataValue()).thenReturn(-5);
        assertThrows(IllegalArgumentException.class, () -> testComputation.computeResult(mockRequest));
    }
}
