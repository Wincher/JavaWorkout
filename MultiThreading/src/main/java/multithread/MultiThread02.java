package multithread;

/**
 * @author wincher
 * @date 24/08/2017
 *
 * 关键字synchronized取得的锁都是对象锁，而不是把一段代码(方法)当作锁，
 * 所以代码中哪个线程先执行synchronized关键字的方法，哪个线程就持有该方法所属对象的锁(Lock)，
 *
 * 在静态方法上加synchronized关键字，表示锁定.calss类，类级别的锁(独占.class类).
 * 替换t2线程run方法为m1.printNum后,printNum去掉static依然可以保证线程同步,即验证了上面一行所说的理论.
 */
public class MultiThread02 {
    /**
     *  static
     */
    private static int num = 0;
    /**
     *  static
     */
    public synchronized void printNum(String tag){
        final String A_SYMBOL = "a";
        try {
            if (A_SYMBOL.equals(tag)){
                num = 100;
                System.out.println("tag a, set num over!");
                Thread.sleep(1000);
            } else {
                num = 200;
                System.out.println("tag b, set num over!");
            }
            System.out.println("tag " + tag + ", num = " + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 注意观察run方法输出顺序
     * @param args
     */
    public static void main(String[] args) {
        MultiThread02 m1 = new MultiThread02();
        //MultiThread02 m2 = new MultiThread02();

        System.out.println(111);
        Thread t1 = new Thread(() -> {
            m1.printNum("a");
        });
        Thread t2 = new Thread(() -> {
            m1.printNum("b");
        });
        t1.start();
        t2.start();
    }

}
