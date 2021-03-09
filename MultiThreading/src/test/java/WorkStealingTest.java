import lombok.SneakyThrows;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wincher
 * <p> PACKAGE_NAME <p>
 */
public class WorkStealingTest {

    private static ConcurrentHashMap<Double, String> map = new ConcurrentHashMap<>();
    private static ThreadLocal<String> th = new ThreadLocal();

    @Test
    public void testWithSingleThread() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        PrimeNumbers task = new PrimeNumbers(1000, atomicInteger);
        task.findPrimeNumbers();
        System.out.println("result:" + task.noOfPrimeNumbers());
    }

    @Test
    public void testWorkStealingWithForkJoinPool() throws InterruptedException {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        PrimeNumbers task = new PrimeNumbers(100000, new AtomicInteger(0));
        commonPool.execute(task);
        commonPool.awaitTermination(100, TimeUnit.SECONDS);
        System.out.println("result: " + task.noOfPrimeNumbers());
        System.out.println(map);
    }

    @Test
    public void testWorkStealingWithExecutors() {
        ExecutorService workStealingPool = Executors.newWorkStealingPool();
//        workStealingPool.execute(new PrimeNumbers(1000));
    }

    class PrimeNumbers extends RecursiveAction {

        private int lowerBound = 0;
        private int upperBound;
        private int granularity = 10;
        private AtomicInteger noOfPrimeNumbers;

        PrimeNumbers(int lowerBound, int upperBound, AtomicInteger noOfPrimeNumbers) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.noOfPrimeNumbers = noOfPrimeNumbers;
        }

        private List<PrimeNumbers> subTasks() {
            List<PrimeNumbers> subTasks = new ArrayList<>();
            System.out.println("upperBound: " + upperBound + ", granularity: " + granularity);
            for (int i = 1; i <= this.upperBound / granularity; i++) {
                int upper = i * granularity;
                int lower = upper - granularity + 1;
                subTasks.add(new PrimeNumbers(lower, upper, noOfPrimeNumbers));
            }
            return subTasks;
        }

        public PrimeNumbers(int upperBound, AtomicInteger atomicInteger) {
            this.upperBound = upperBound;
            this.noOfPrimeNumbers = atomicInteger;
        }

        @SneakyThrows
        @Override
        protected void compute() {
            if (((upperBound + 1) - lowerBound) > granularity) {
                ForkJoinTask.invokeAll(subTasks());
            } else {
                findPrimeNumbers();
            }
        }

        void findPrimeNumbers() throws InterruptedException {
            if (0 == lowerBound) {
                lowerBound++;
            }
            double v = ThreadLocalRandom.current().nextDouble();
            String value = String.valueOf(v);
            th.set(value);
            if (map.containsKey(v)) {
                map.put(v, map.get(v) + Thread.currentThread().getName());
                System.out.println("asdfasdf" + v);
            } else {
                map.put(v, Thread.currentThread().getName());
            }
            TimeUnit.MILLISECONDS.sleep(40);
            for (int num = lowerBound; num <= upperBound; num++) {
                if (isPrime(num)) {
                    //System.out.println(num + ":" + Thread.currentThread().getName() + ",");
                    noOfPrimeNumbers.getAndIncrement();
                }
            }
            String s = th.get();
            if (!s.equals(value)) {
                System.err.println("error");
            }
        }

        private boolean isPrime(int num) {
            for (int i = 2; i <= num / 2; ++i) {
                // condition for non-prime number
                if (num % i == 0) return false;
            }
            return true;
        }

        public int noOfPrimeNumbers() {
            return noOfPrimeNumbers.intValue();
        }
    }
}
