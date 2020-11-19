package test;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

/**
 * @author wincher
 * @date 2020/3/25
 * <p> test <p>
 */
public class TestBigDecimal {
	public static void main(String[] args) {
		System.out.println(BigDecimal.valueOf(500.51).setScale(3, HALF_UP));
		System.out.println(BigDecimal.ONE.compareTo(new BigDecimal(1)));
		
		System.out.println(new BigDecimal("0.0"));
	}
}
