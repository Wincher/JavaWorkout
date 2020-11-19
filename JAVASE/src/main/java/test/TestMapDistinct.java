package test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wincher
 * @date 2020/3/26
 * <p> test <p>
 */
public class TestMapDistinct {
	public static void main(String[] args) {
		A a = new A("asdf", "asdf");
		A b = new A("", "asdf");
		A c = new A(null, "asdf");
		A d = new A("asdf", "asdf");
		System.out.println(Arrays.asList(a, b, c, d).stream().map(item -> item.a).distinct().collect(Collectors.joining(",")));
		Stream<String> stringStream = Arrays.asList(a, b, c, d).stream().map(item -> item.a);
		System.out.println(stringStream.count());
		System.out.println(stringStream.distinct().count());
	}
}

class A {
	String a;
	String b;
	
	A(String a, String b) {
		this.a = a;
		this.b = b;
	}
}
