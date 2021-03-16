package PECS;

import PECS.entity.Father;
import PECS.entity.GrandSon;
import PECS.entity.Son;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * I was curious, that why java.util.stream.Stream#map using parameters this way -> Function<? super T, ? extends R> mapper
 * @author wincher
 * <p> PECS <p>
 */
public class streamMapGenericTypesDemo {

    public static void main(String[] args) {
        Stream<Son> stream = Stream.of(new Son("son"), new GrandSon("grandson"));
        // 这里T是Son, 这里面可以显示这顶泛型, 由于 ? extends R 以及producer extends, 只可以指定为 R 及 R 的父类
        Stream<Father> fatherStream = stream.<Father>map(a -> new Son(a.getName()));
        String collect = stream.<Father>map(a -> new Son(a.getName()))
            .map(String::valueOf)
            .collect(Collectors.joining("\n\r"));
        System.out.println(collect);
    }

}
