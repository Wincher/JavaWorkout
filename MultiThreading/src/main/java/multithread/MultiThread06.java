package multithread;

/**
 * @author wincher
 * @date 24/08/2017
 */
public class MultiThread06 extends Thread {
    /** volatile */
    private volatile boolean isRunning = true;
    private void setRunning(boolean isRunning){
        this.isRunning = isRunning;
    }
    @Override
    public void run(){
        System.out.println("enter run method..");
        while (isRunning == true) {
            boolean a = isRunning;
            // 加上 System.out.println 后会出现即使没有为isRunning加上volatile也会回去到isRunning变为false,
            // 可能是 System.out.println 里面的方法都是加锁的同步了主内存和工作内存的数据(只是猜测,详细机制没有深入了解)
            
           System.out.println("---");
        }
        System.out.println("thread stop...");
    }

    public static void main(String[] args) throws InterruptedException {
        MultiThread06 m = new MultiThread06();
        m.start();
        Thread.sleep(1000);
        m.setRunning(false);
        System.out.println("isRunning is false now");
        Thread.sleep(1000);
        System.out.println(m.isRunning);
    }
}
