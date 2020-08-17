package test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TestSet {
	public static void main(String[] args) {
		
		Set<String> set = new HashSet<>();
		Set<String> set2 = new HashSet<>();
		set.add("");
		set.add("");
		set.add("1");
		set.add(null);
		System.out.println(set.size());
		set.forEach(System.out::println);
		set.stream().map(val -> {
			if (null == val) {
				
			}
			return val;
		}).collect(Collectors.toSet());
	}
}
