package test;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * TestReference this use jps, find pid, and exec `kill -15 <pid>`
 * Find Supported signal: kill -l
 * @author wincher
 * @date 2019/07/31
 * <p> SignalTest <p>
 */
public class SignalTest implements SignalHandler {

    /*
        HUP 1 终端断线
        INT 2 中断（同 Ctrl + C）
        QUIT 3 退出（同 Ctrl + \）
        TERM 15 终止
        KILL 9 强制终止
        CONT 18 继续（与STOP相反， fg/bg命令）
        STOP 19 暂停（同 Ctrl + Z）
     */
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
