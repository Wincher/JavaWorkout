package test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wincher
 * @since 2020/2/6
 * <p> test <p>
 */
public class TestLocalDateTime {
	public static void main(String[] args) {
		LocalDateTime ldt = LocalDateTime.now();
		ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	}
}
