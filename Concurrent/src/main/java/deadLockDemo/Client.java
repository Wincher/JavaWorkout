package deadLockDemo;

import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author wincher
 * <p> deadLockDemo <p>
 */
@Log
public class Client {

    private final ConcurrentMap<String, Item> items = new ConcurrentHashMap<>();
    private final AtomicInteger locked = new AtomicInteger();
    private final AtomicInteger unlocked = new AtomicInteger();

    public Client() {
        IntStream.range(0, 10).forEach(i -> items.put("item" + i, new Item("item" + i)));
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.order();
    }

    public void order() {
        long begin = System.currentTimeMillis();
        long success = IntStream.rangeClosed(1, 100).parallel()
            .mapToObj(i -> {
                List<Item> cart = createCart().stream()
                    //加上sorted后锁按顺序加在不会出现死锁, 极大的性能提升
                    .sorted(Comparator.comparing(Item::getName))
                    .collect(Collectors.toList());
                return createOrder(cart);
            })
            .filter(result -> result)
            .count();

        //成功的次数加上createOrder中unLocked次数应为总数
        log.info("success:{" + success +
                "}\n\r totalRemaining:{" +
                items.entrySet().stream().map(item -> item.getValue().remaining).reduce(0, Integer::sum) +
                "}\n\r took:{" + (System.currentTimeMillis() - begin) +
                "}ms \n\r items:{" + items + "}"
            );
    }

    private List<Item> createCart() {
        return IntStream.rangeClosed(1, 3)
            .mapToObj(i -> "item" + ThreadLocalRandom.current().nextInt(items.size()))
            .map(items::get)
            .collect(Collectors.toList());
    }


    private boolean createOrder(List<Item> order) {
        List<ReentrantLock> locks = new ArrayList<>();

        for (Item item : order) {
            try {
                if (item.lock.tryLock(10, TimeUnit.SECONDS)) {
                    locks.add(item.lock);
                    System.out.println("locked: " + locked.incrementAndGet());
                } else {
                    locks.forEach(ReentrantLock::unlock);
                    System.out.println("unlocked: " + unlocked.incrementAndGet());
                    return false;
                }
            } catch (InterruptedException e) {
                log.warning("error le " + e.getMessage());
            }
        }
        try {
            order.forEach(item -> item.remaining--);
        } finally {
            locks.forEach(ReentrantLock::unlock);
        }
        return true;
    }

}
