import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

/**
 * @author huwq
 * @since 2018/8/12
 * <p> PACKAGE_NAME <p>
 */
public class Stream {
  public static void main(String[] args) {
    List<String> list = Arrays.asList("aba", "bab", "3cc", "d1d", "4ee", "1ff");
    list.stream().filter(a  -> !a.equals("aa")).forEach(System.out::println);
    list.stream().sorted().forEach(System.out::println);
    LongStream longStream = LongStream.rangeClosed(1, 100_000_1000).parallel();
    long sum = longStream.sum();
    System.out.println(sum);
  }
}
