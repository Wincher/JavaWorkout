package concurrentMap;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @author wincher
 * <p> concurrentMap <p>
 */
public class TestConcurrentHashMap {

    private final static int THREAD_COUNT = 10;
    private final static int ITEM_COUNT = 10000;

    private static ConcurrentHashMap<String, Long> getData(int count) {
        return LongStream.rangeClosed(1, count)
            .boxed()
            .collect(Collectors.toConcurrentMap(i -> UUID.randomUUID().toString(), Function.identity(), (o1, o2) -> o1, ConcurrentHashMap::new));
    }

    public static void main(String[] args) throws InterruptedException {
        //其实道理显而易见,根本没必要这样来实验, 只要知道ConcurrentHashMap 只能保证原子性的读写操作是线程安全的,
        //并不能保证多操作的线程安全, 需要手动同步保证, 但是提供了原子性方法 putIfAbsent 等做复合逻辑
        //那么 ConcurrentHashMap存在的意义在哪儿呢,我们都知道是线程安全的Map, 怎么就不安全了呢
        //最主要的是避免了在并发场景下HashMap的回环链表问题
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 90000);
        System.out.println("init size: " + concurrentHashMap.size());
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 10).parallel().forEach( i -> {
            int gap = ITEM_COUNT - concurrentHashMap.size();
            while (0 < gap) {
                //去掉同步代码块和dobule check就回出现线程安全问题, 导致最后并不能准确的将concurrentHashMap填满到ITEM_COUNT, 而是会溢出
                synchronized (concurrentHashMap) {
                    gap = ITEM_COUNT - concurrentHashMap.size();
                    if (0 < gap) {
                        System.out.println("gap size: " + gap + " worker:" + i);
                        concurrentHashMap.putAll(getData(1));
                    }
                }
            }
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("finish size: " + concurrentHashMap.size());
    }
}
