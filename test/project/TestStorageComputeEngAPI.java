package project;

import datastore.StorageComputeEngAPI;
import datastore.StorageComputeEngAPIImplementation;
import datastore.StoreRequest;
import compute.eng.ComputationAPI;
import compute.eng.ComputationResult;
import user.Source;
import user.Destination;
import datastore.IntData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.fail;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;

public class TestStorageComputeEngAPI {
    @TempDir
    Path tempDir;

    @Test
    public void testRead() throws Exception {
        ComputationAPI compAPI = Mockito.mock(ComputationAPI.class);
        StorageComputeEngAPI storageAPI = new StorageComputeEngAPIImplementation(compAPI);

        // Join the input file and write
        Path inputFile = tempDir.resolve("input.txt");
        Files.writeString(inputFile, "25");

        Source source = Mockito.mock(Source.class);
        when(source.getPath()).thenReturn(inputFile.toString());

        StoreRequest request = Mockito.mock(StoreRequest.class);
        when(request.getSource()).thenReturn(source);

        IntData result = storageAPI.read(request);

        Assertions.assertEquals(result.getIntAt(0), 25);
    }

    @Test
    public void testWrite() throws Exception {
        ComputationAPI compAPI = Mockito.mock(ComputationAPI.class);
        StorageComputeEngAPI storageAPI = new StorageComputeEngAPIImplementation(compAPI);

        Path outputFile = tempDir.resolve("output.txt");

        Destination destination = Mockito.mock(Destination.class);
        when(destination.getPath()).thenReturn(outputFile.toString());

        StoreRequest request = Mockito.mock(StoreRequest.class);
        when(request.getDestination()).thenReturn(destination);

        ComputationResult result = Mockito.mock(ComputationResult.class);
        when(result.getGreatestPrimeFactor()).thenReturn(5);

        storageAPI.write(request, result);

        Assertions.assertEquals("5", Files.readString(outputFile));
    }
}
