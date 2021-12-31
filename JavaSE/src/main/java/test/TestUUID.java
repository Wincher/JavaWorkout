package test;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestUUID {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(UUID.randomUUID().toString());
    }
}
