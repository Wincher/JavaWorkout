package test;

import java.util.Optional;

public class TestEnum {
    public static void main(String[] args) {
    
        Color r = Color.valueOf("RED");
        Color g = Color.valueOf("RED");
        System.out.println(r == g);
        String a = "asdf";
        String s = Optional.ofNullable(a).orElse("");
        System.out.println(s);
        Color b = Color.valueOf(null);
        Color c = null;
        System.out.println(c.name());
        
    }
}

enum Color {
    RED,
    YELLOW,
    GREEN
}
