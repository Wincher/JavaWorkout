package test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wincher
 * <p> test <p>
 */
public class TestSecureRandom {
    public static void main(String[] args) {
        List<String> cache = new ArrayList();
        SecureRandom ng = new SecureRandom();

        for (int i = 0; i < 100000; i++) {
            byte[] randomBytes = new byte[3];
            ng.nextBytes(randomBytes);
            System.out.println(randomBytes.hashCode() + " : " + Arrays.hashCode(randomBytes));
            String x = Arrays.toString(randomBytes);
//            if (0 < cache.indexOf(x)) {
//                System.err.println(x + " :duplicated");
//            } else {
//                cache.add(x);
//            }
            //System.out.println(x);
        }
    }
}
