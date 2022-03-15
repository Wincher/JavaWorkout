package feature.java15;

/**
 * @author wincher
 * <p> feature.java15 <p>
 */
public class TryTextBlock {
    public static void main(String[] args) {
        String a = """
            this is
                a
                te\
    xt
            with
    mutiple lines
        """;
        System.out.println(a);
    }
}
