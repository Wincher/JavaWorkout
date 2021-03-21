package cn.wincher.my_reactor;

import java.util.concurrent.Executors;

/**
 * @author wincher
 * <p> cn.wincher.my_reactor <p>
 */
public final class Schedulers {

    private static final Scheduler ioScheduler = new Scheduler(Executors.newSingleThreadExecutor());

    public static Scheduler io() {
        return ioScheduler;
    }
}
