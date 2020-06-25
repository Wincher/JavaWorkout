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
        
//        Color a = null;
//        System.out.println(a.name());
        
        String color = "red_re";
        Color color1 = null;
        try {
            color1 = Color.valueOf(color.toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == color1) {
        
        } else {
            System.out.println(color1);
        }
    
    }
}

enum Color {
    RED,
    YELLOW,
    GREEN
}
