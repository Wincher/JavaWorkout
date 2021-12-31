package concurrentMap;

import java.util.HashMap;
import java.util.Map;
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
        //可以看到
        HashMap<Integer, Integer> map = new HashMap<>();
        testIt(map);
        ConcurrentHashMap<Integer, Integer> map1 = new ConcurrentHashMap<>();
        testIt(map1);
        //这里多做几次实验就会发现会出现不一致的情况, 这就是HashMap非线程安全的问题所在了, 并发条件下Hash冲突可能丢失元素, 还可能size与实际size不一致
        System.out.println(map.size() + ":" + map.keySet().stream().count());
//        其实道理显而易见,根本没必要这样来实验, 只要知道ConcurrentHashMap 只能保证原子性的读写操作是线程安全的,
//        并不能保证多操作的线程安全, 需要手动同步保证, 但是提供了原子性方法 putIfAbsent 等做复合逻辑
//        那么 ConcurrentHashMap存在的意义在哪儿呢,我们都知道是线程安全的Map, 怎么就不安全了呢
//        其中之一是避免了在并发场景下HashMap的回环链表问题
//        回环链表问题是因为并发的resize, 并且由于链表尾遍历, 导致 B -> A -> B的问题出现死循环的问题, JDK1.8后已经增加了tail指针避免了死循环问题
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 90000);
        System.out.println("init size: " + concurrentHashMap.size());
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 10).parallel().forEach( i -> {
            int gap = ITEM_COUNT - concurrentHashMap.size();
            while (0 < gap) {
                //double check就回出现线程安全问题, 导致最后并不能准确的将concurrentHashMap填满到ITEM_COUNT, 而是会溢出
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

    public static void testIt(Map<Integer, Integer> map) throws InterruptedException {
        IntStream.range(0, 100).parallel().forEach(i -> map.put(i, -1));
        System.out.println(map.size());
    }

}
