import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

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
                   System.err.println(e.getMessage());
               }
               System.out.println(Calendar.getInstance().getTime());
                Thread.getAllStackTraces().keySet().forEach(t -> System.out.println(t.getName()));
            }
        }).start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Main thread stop");
    }
}
