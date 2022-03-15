package feature.java14;

/**
 * @author wincher
 * <p> feature.java14 <p>
 */
public class TrySwitch {
    public static void main(String[] args) {
        int i = 3;
        switch (i) {
            case 1 -> System.out.println("one");
            case 2 -> System.out.println("two");
            default -> System.out.println("more");
        }
        String s = switch (i) {
            case 1 -> "one";
            case 2 -> "two";
            default -> "more";
        };
        System.out.println(s);
    }
}
