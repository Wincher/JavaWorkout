package test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestStream {
	
	public static void main(String[] args) {
		List<String> collect = Arrays.asList(null,
			new A("123123", "asdffd"),
			new A("123123", "asdffd"),
			new A("123123", "asdffd"),
			new A("123", "asdffd")).stream().map(TestStream::convert).collect(Collectors.toList());
		collect.forEach(System.out::println);
	}
	
	public static String convert(A val) {
		return String.valueOf(val.a.charAt(3));
	}
	
	static class A {
		String a;
		String b;
		public A(String a, String b) {
			this.a = a;
			this.b = b;
		}
	}
}
