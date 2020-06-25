package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author wincher
 * @since 2020/3/18
 * <p> test <p>
 */
public class TestSet {
	
	public static void main(String[] args) {
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
}
