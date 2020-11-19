package test;

import java.text.Format;

/**
 * @author wincher
 * @date 2020/7/13
 * <p> test <p>
 */
public class TestStringFormat {
	
	private static final String DECISION_LOG_FORMAT = "Blacklist: %s %s %d";
	
	public static void main(String[] args) {
		System.out.println(String.format(DECISION_LOG_FORMAT, 1, "a", 3));
	}
}
