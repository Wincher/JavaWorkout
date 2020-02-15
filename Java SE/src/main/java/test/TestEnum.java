package test;

public class TestEnum {
    public static void main(String[] args) {
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
    RED_RED,
    YELLOW,
    GREEN
}
