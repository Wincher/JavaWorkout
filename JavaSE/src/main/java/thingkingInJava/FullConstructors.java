package thingkingInJava;
/**
 * @author wincher
 * @date   2017/6/26.
 * src:12.4创建自定义异常
 */
public class FullConstructors {
    public static void f() throws MyException {
        System.out.println("Throwing MyException from f()...");
        throw new MyException();
    }
    public static void g() throws MyException {
        System.out.println("Throwing MyException from g()...");
        throw new MyException("Originated in g()");
    }
    public static void main(String[] args) {
        try {
            f();
        } catch (MyException e) {
            e.printStackTrace();
        }
        try {
            g();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}

class MyException extends Exception {
    public MyException() {}

    public MyException(String message) { super(message); }
}
