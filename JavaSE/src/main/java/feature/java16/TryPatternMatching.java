package feature.java16;

/**
 * @author wincher
 * <p> feature.java16 <p>
 */
public class TryPatternMatching {
    public static void main(String[] args) {
        Animal a = new Cat();
//        Animal a = new Animal();
        if (a instanceof  Cat) {
            Cat c = (Cat) a;
            System.out.println(c.getName());
        } else {
            System.out.println("not a Cat");
        }
        if (a instanceof Cat c) {
            System.out.println(c.getName());
        } else {
            System.out.println("not a Cat");
        }

    }

    static class Animal {

    }
    static class Cat extends Animal {
        private static final String name = "kitty";

        public String getName() {
            return name;
        }
    }
}
