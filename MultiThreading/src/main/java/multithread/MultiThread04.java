package multithread;

/**
 * @author  wincher
 * @date 24/08/2017
 * DirtyRead:
 * 如果getValue方法不加锁那么由于setValue中途停止只赋值了username，那么getValue获取的password值还是旧数据，
 * 这就产生了DirtyRead
 */
public class MultiThread04 {
    private String username = "wincher";
    private String password = "123456";

    public synchronized  void setValue(String username, String password){
        this.username = username;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.password = password;

        System.out.println("setValue result is : username = " + username + " , password = " + password);
    }
    /**
     *  synchronized
     */
    public synchronized void getValue() {

        System.out.println("getValue result is : username = " + username + " , password = " + password);
    }

    public static void main(String[] args) throws InterruptedException {
        MultiThread04 m = new MultiThread04();
        new Thread(() -> m.setValue("Messi","123")).start();
        Thread.sleep(1000);
        m.getValue();
    }
}
