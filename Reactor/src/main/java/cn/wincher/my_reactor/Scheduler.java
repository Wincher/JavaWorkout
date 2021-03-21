package cn.wincher.my_reactor;

import java.util.concurrent.Executor;

/**
 * @author wincher
 * <p> cn.wincher.my_reactor <p>
 */
public class Scheduler {

    final Executor executor;

    public Scheduler(Executor executor) {
        this.executor = executor;
    }

    public Worker createWorker() {
        return new Worker(executor);
    }

    public static class Worker {
        final Executor executor;
        public Worker(Executor executor) {
            this.executor = executor;
        }
        //TODO: encapsulation Runnable to Action like RxJava
        public void schedule(Runnable runnable) {
            executor.execute(runnable);
        }
    }}
