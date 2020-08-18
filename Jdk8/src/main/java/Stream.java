import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
  
  
    String[] tmp = new String[]{"a", "b", "c"};
    System.out.println(Arrays.stream(tmp).collect(Collectors.joining("&states=", "&states=", "")));
    
    List<Integer> numbers = Arrays.asList(1,2,3);
    List<String> collect = numbers.stream().map(i -> get(i)).flatMap(a -> a.stream()).collect(Collectors.toList());
    List<String> collect2 = numbers.stream().flatMap(i -> get(i).stream()).collect(Collectors.toList());
    System.out.println("");
  }
  
  public static List<String> get(int num) {
    List<String> strings = new ArrayList<>();
    String[] tmp = new String[]{"a", "b", "c"};
    for (int i = 0; i < num; i++) strings.add(tmp[i]);
    return strings;
  }
}
