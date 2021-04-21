package deepintojvm;

/**
 * @author wincher
 * <p> deepintojvm <p>
 */
public class Ch03ClassInitial {

    private Ch03ClassInitial() {}

    /**
     * -verbose:class
     * 类的初始化何时会被触发呢?JVM 规范枚举了下述多种触发情况:
     * 1. 当虚拟机启动时，初始化用户指定的主类;
     * 2. 当遇到用以新建目标类实例的 new 指令时，初始化 new 指令的目标类;
     * 3. 当遇到调用静态方法的指令时，初始化该静态方法所在的类;
     * 4. 当遇到访问静态字段的指令时，初始化该静态字段所在的类;
     * 5. 子类的初始化会触发父类的初始化;
     * 6. 如果一个接口定义了 default 方法，那么直接实现或者间接实现该接口的类的初始化，
     * 会触发该接口的初始化;
     * 7. 使用反射 API 对某个类进行反射调用时，初始化这个类;
     * 8. 当初次调用 MethodHandle 实例时，初始化该 MethodHandle 指向的方法所在的类。
     * 只有当调用 Singleton.getInstance 时，程序才会访问 LazyHolder.INSTANCE，才会触发对
     * LazyHolder 的初始化(对应第 4 种情况)，继而新建一个实例。
     * 由于类初始化是线程安全的，并且仅被执行一次，因此程序可以确保多线程环境下有且仅有 一个实例。
     */
    private static class LazyHolder {
        static final Ch03ClassInitial INSTANCE = new Ch03ClassInitial();
        static {
            System.out.println("LazyHolder.<clinit>");
        }
    }
    public static Object getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static Object getInstance(boolean flag) {
        //this show that new array will not invoke Class Initial
        return flag ? new LazyHolder[2] : LazyHolder.INSTANCE;
    }

    public static void main(String[] args) {
        Ch03ClassInitial.getInstance(true);
        System.out.println("--------------------");
        Ch03ClassInitial.getInstance(false);
    }
}
