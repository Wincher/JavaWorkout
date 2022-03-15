package feature.java17;

/**
 * @author wincher
 * <p> feature.java17 <p>
 */
public class TeySealedClass {

    public static void main(String[] args) {
        //just make sure syntax works fine
        Animal a = new Cat();
        System.out.println(a);
    }

    static sealed class Animal permits Cat {

    }
    static final class Cat extends Animal {

    }
}
