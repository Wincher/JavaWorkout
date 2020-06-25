package test;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * TestReferrence this use jps, find pid, and exec `kill -15 <pid>`
 * Find Supported signal: kill -l
 * @author huwq
 * @since 2019/07/31
 * <p> SignalTest <p>
 */
public class SignalTest implements SignalHandler {

    public static void main(String[] args) throws InterruptedException {
        SignalTest signalHandler = new SignalTest();
        // install handler
        Signal.handle(new Signal("TERM"), signalHandler);
        // this code will occure: `Exception in thread "main" java.lang.IllegalArgumentException: Signal already used by VM or OS: SIGUSR1`, still dont know why.
        //Signal.handle(new Signal("USR1"), signalHandler);
        Signal.handle(new Signal("USR2"), signalHandler);

        for ( ; ; ) {
            Thread.sleep(5000);
            System.out.println("running......");
        }
    }

    @Override
    public void handle(Signal signal) {
        signalCallback(signal);
    }

    private void signalCallback(Signal signal) {
        System.out.println(signal.getName() + " is received!");
    }
}
