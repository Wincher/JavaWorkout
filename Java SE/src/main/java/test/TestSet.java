package test;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wincher
 * @since 2020/3/18
 * <p> test <p>
 */
public class TestSet {
	
	public static void main(String[] args) {
		Set<Integer> ints = new HashSet<>();
		System.out.println(ints.add(1));
		System.out.println(ints.add(2));
		System.out.println(ints.add(1));
		ints = ints.stream().map(i->i*2).collect(Collectors.toSet());
		System.out.println(ints);
		Map<String,String> src = new HashMap<>(8);
		src.put("1", "2");
		src.put("2", "3");
		System.out.println(src.get("asf"));
		Map<String,String> target = new HashMap<>(8);
		String srcVal = "a";
		String targetVal = "b";
		System.out.println(null == srcVal ^null == targetVal);
	}
	
	private Set<String> getModifiedExtrasKey(Map<String, String> src, Map<String, String> target) {
		if (null != src && !src.isEmpty() ^ (null != target && !target.isEmpty())) {
			return new HashSet<>(0);
		}
		Set<String> tmp1 = new HashSet<>(src.keySet());
		Set<String> tmp2 = new HashSet<>(target.keySet());
		tmp1.removeAll(target.keySet());
		tmp2.removeAll(src.keySet());
		tmp1.addAll(tmp2);
		return tmp1;
	}
	
	
	@Test
	public void testStreamMap() {
		Set<String> set = new HashSet<>();
		Set<String> set2 = new HashSet<>();
		set.add("");
		set.add("");
		set.add("1");
		set.add(null);
		System.out.println(set.size());
		set.forEach(System.out::println);
		Set<String> collect = set.stream().map(val -> {
			if (null == val) {
			
			}
			return val;
		}).collect(Collectors.toSet());
		System.out.println(collect);
	}
}
