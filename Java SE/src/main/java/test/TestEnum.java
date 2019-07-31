package test;

public class TestEnum {
    public static void main(String[] args) {
        Color a = null;
        System.out.println(a.name());
    }
}

enum Color {
    RED,
    YELLOW,
    GREEN
}
