package test;

import com.sun.javafx.binding.StringFormatter;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

/**
 * @author wincher
 * @date 2020/6/29
 * <p> test <p>
 */
public class TestArgs {
	
	public static void main(String[] args) {
		System.out.println(String.format("asdfasdf\n%s", "a"));
		test();
		test(null);
		test("a");
		test("a", "b");
	}
	
	static void test(String... args) {
		System.out.println(args);
	}
	
}
