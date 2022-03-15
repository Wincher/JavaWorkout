package feature.java8;

import java.util.Optional;

/**
 * @author wincher
 * <p> features.java8 <p>
 */
public class TryOptional {
    public static void main(String[] args) {
        Optional.ofNullable("ok").orElseThrow(() -> new RuntimeException("not ok"));
        System.out.println(Optional.ofNullable(null).orElse("asdf"));
    }
}
