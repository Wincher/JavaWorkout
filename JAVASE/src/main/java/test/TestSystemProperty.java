package test;

/**
 * @author wincher
 * <p> test <p>
 */
public class TestSystemProperty {
    public static void main(String[] args) {
        String userDir = System.getProperty("user.dir");
        System.out.println(userDir);
    }
}
