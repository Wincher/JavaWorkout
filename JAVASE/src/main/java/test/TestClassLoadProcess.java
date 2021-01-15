package test;

/**
 * @author wincher
 * <p> test <p>
 */
public class TestClassLoadProcess {
    /*
     the result show that in a jvm, every static block run one time for on Class,
     and when new instance for class init method always call parent's init method first.\
     */
    public static void main(String[] args) {
        Super s = new Sub();
        s = new Sub();
        s = new Super();
    }
}

class Super {
    static {
        System.out.println("1");
    }
    public  Super() {
        System.out.println("2");
    }
}

class Sub extends Super {
    static {
        System.out.println("a");
    }
    public Sub() {
        System.out.println("b");
    }
}
