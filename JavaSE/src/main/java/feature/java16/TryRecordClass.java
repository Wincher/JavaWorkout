package feature.java16;

/**
 * https://openjdk.java.net/jeps/359 14 Preview
 * https://openjdk.java.net/jeps/384 15 Second Preview
 * https://openjdk.java.net/jeps/395 16 Release
 * @author wincher
 * <p> feature.java16 <p>
 */
public class TryRecordClass {

    public static void main(String[] args) {
        Cat cat = new Cat("Kitty");
        System.out.println(cat.name());
    }

    record Cat(String name) {
        
    }
}
