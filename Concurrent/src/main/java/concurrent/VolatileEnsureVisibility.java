package concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author wincher
 * @date 24/08/2017
 */
public class VolatileEnsureVisibility extends Thread {
    
    //remove volatile to observe
    //现代处理器使用复杂的缓存一致性协议（如 MESI）来保证缓存之间的数据一致性。
    //即使没有 volatile，缓存一致性协议也可能将 isRunning 的变化传播到其他线程的缓存中。
    private boolean isRunning = true;
    //使用 Unsafe 类来显式地使用 volatile 语义，这会更可靠的展示volatile关键字的效果。
    //todo: ?实际上isRunning加不加volatile一样可以读取到isRunning的最新值,从而中断线程,具体原因可能有缓存一致性协议,JVM 的优化.JMM 的可见性规则,简单的测试环境中，线程之间的竞争可能不够激烈，因此很难观察到可见性问题。在更复杂的并发场景中，可见性问题更容易出现,先不纠结了
    private static Unsafe unsafe;
    private static long isRunningOffset;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            // 获取 isRunning 变量在 VolatileEnsureVisibility 对象中的内存偏移量
            isRunningOffset = unsafe.objectFieldOffset(VolatileEnsureVisibility.class.getDeclaredField("isRunning"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setRunning(boolean isRunning) {
        unsafe.putBooleanVolatile(this, isRunningOffset, isRunning);
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + ": enter run method..");
        while (unsafe.getBooleanVolatile(this, isRunningOffset)) {
        }
        System.out.println(Thread.currentThread().getName() + ": thread stop...");
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileEnsureVisibility vev = new VolatileEnsureVisibility();
        Thread thread = new Thread(vev::run);
        thread.start();
        Thread.sleep(2000);
        vev.setRunning(false);
        System.out.println(Thread.currentThread().getName() + ": isRunning set false now");
        thread.join();
        System.out.println(vev.isRunning);
    }
}
