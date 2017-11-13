package MultiThread;

/**
 * Created by wincher on 24/08/2017.
 */
public class MultiThread06 extends Thread {
    /** volatile */
    private volatile boolean isRunning = true;
    private void setRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

    public void run(){
        System.out.println("enter run method..");
        while (isRunning == true) {
            boolean a = isRunning;
            //加上sout后会出现即使没有为isRunning加上volatile也会回去到isRunning变为false ***原因暂时不知道
            System.out.println("---");
        }
        System.out.println("thread stop..");
    }

    public static void main(String[] args) throws InterruptedException {
        MultiThread06 m = new MultiThread06();
        m.start();
        Thread.sleep(3000);
        m.setRunning(false);
        System.out.println("isRunning is false now");
        Thread.sleep(1000);
        System.out.println(m.isRunning);
    }
}
