package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TestCompletableFuture {

    public static void main(String[] args) throws InterruptedException {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync( () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Messi";
        });
        CompletableFuture<String> greetingFuture = completableFuture.thenApplyAsync( name -> "hello, " + name);
        CompletableFuture<String> combinedFuture = greetingFuture.thenCombine(greetingFuture, String::concat);
        new Thread( () -> {
            try {
                System.out.println(combinedFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("before sleep..");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("after sleep..");

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "abc");
        CompletableFuture<String> f =  future.thenCombine(future2, (x,y) -> x + "-" + y);
        try {
            System.out.println(f.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
