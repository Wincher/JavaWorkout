package concurrent;

/**
 * @author  wincher
 * @date 24/08/2017
 * DirtyRead:
 * 如果getValue方法不加锁那么由于setValue中途停止只赋值了username，那么getValue获取的password值还是旧数据，
 * 这就产生了DirtyRead
 */
public class DirtyRead {
    private String username = "wincher";
    private String password = "123456";

    public synchronized void setValue(String username, String password){
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
     *  adjust weather the method is sync modified
     *  to see dirty read occurs
     */
    public synchronized void getValue() {
        System.out.println("getValue result is : username = " + username + " , password = " + password);
    }

    public static void main(String[] args) throws InterruptedException {
        DirtyRead m = new DirtyRead();
        new Thread(() -> m.setValue("Messi","123")).start();
        Thread.sleep(1000); //simulate user.name set,user.password not set
        m.getValue();
    }
}
