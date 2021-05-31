package deepintojvm;

/**
 * @author wincher
 * <p> deepintojvm <p>
 */
public class Ch14Synchronized {
    // Run with -XX:+UnlockDiagnosticVMOptions -XX:+PrintBiasedLockingStatistics -XX:TieredStopAtLevel=1
    // -XX:BiasedLockingStartupDelay=0
    static Lock lock = new Lock();
    static int counter = 0;
    public static void foo() {
        synchronized (lock) {
            counter++; }
    }

    /**
     * # total entries: 0
     * # biased lock entries: 0
     * # anonymously biased lock entries: 0
     * # rebiased lock entries: 0
     * # revoked lock entries: 0
     * # fast path lock entries: 4142066
     * # slow path lock entries: 3
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        //lock.hashCode(); // Step 2
        //System.identityHashCode(lock); // Step 4
        for (int i = 0; i < 1_000_000; i++) {
            foo(); }
    }
    static class Lock {
        //@Override public int hashCode() { return 0; } // Step 3
    }
}
