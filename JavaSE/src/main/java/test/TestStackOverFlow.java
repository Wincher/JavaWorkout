package test;

public class TestStackOverFlow {
    // -Xss1m
    // -Xss5m

    //栈调用深度
    private static int count;

    public static void recursion() {
        count++;
        recursion();
    }

    public static void main(String[] args) {
        try {
            method2();
        } catch (Throwable t) {
            System.out.println("调用最大深度: " + count );
            t.printStackTrace();
        }
    }

    public static void method1()  throws Throwable {
            recursion();
    }

    public static void method2() throws Throwable {
        method1();
    }
}
