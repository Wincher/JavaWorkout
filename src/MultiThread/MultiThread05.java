package MultiThread;

/**
 * Created by wincher on 24/08/2017.
 * synchronized的重入:
 * 这是没问题的
 */
public class MultiThread05 {
    public synchronized void method1(){
        System.out.println("method1..");
        method2();
    }
    public synchronized void method2(){
        System.out.println("method2..");
        method3();
    }
    public synchronized void method3(){
        System.out.println("method3..");
    }

    public static void main(String[] args) {
        MultiThread05 m = new MultiThread05();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                m.method1();
            }
        });
        t1.start();
    }
}
