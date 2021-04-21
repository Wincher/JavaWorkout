import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @author wincher
 * <p> PACKAGE_NAME <p>
 */
public class SuspendDemo {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
           while (true) {
               try {
                   TimeUnit.SECONDS.sleep(1);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println(Calendar.getInstance());
               Thread.getAllStackTraces();
           }
        }).start();
        TimeUnit.SECONDS.sleep(10);
        System.out.println("Main thread stop");
    }
}
