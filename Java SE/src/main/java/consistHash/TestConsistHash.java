package consistHash;

import java.util.*;

/**
 * @author wincher
 * @since 2020/7/8
 * <p> consistHash <p>
 */
public class TestConsistHash {
	private SortedMap<Integer, String> sortedCircle = new TreeMap<>();
	private static final int virtualCount = 100;
	
	private void mapVirtualNode(List<String> nodes) {
		Map<String, String> virtualNodes = new HashMap<>();
		nodes.stream().forEach( n -> {
			for (int i = 0; i < virtualCount; i++) sortedCircle.put(hash(n+i), n);
		});
		System.out.println(sortedCircle.size());
	}
	
	private void addNode(String node) {
		for (int i = 0; i < virtualCount; i++) sortedCircle.put(hash(node + i), node);
	}
	
	private String findNode(String key) {
		SortedMap<Integer, String> subMap = sortedCircle.tailMap(hash(key));
		return subMap.isEmpty() ? sortedCircle.get(sortedCircle.firstKey()) : subMap.get(subMap.firstKey());
	}
	
	private void stat(Map<String, Object> data) {
		Map<String, Integer> result = new HashMap<>();
		data.keySet().stream().forEach( key -> {
			String nodeName = findNode(key);
			if (result.get(nodeName) == null) {
				result.put(nodeName, 0);
			}
			result.put(nodeName, result.get(nodeName) + 1);
		});
		for (String key : data.keySet()) {
		
		}
		System.out.println(result);
		System.out.println("标准差: " + stdDev(result, data.size()));
	}
	/**
	 * 标准差计算
	 *
	 * @param map
	 * @param totalNums
	 * @return
	 */
	private double stdDev(Map<String, Integer> map, long totalNums) {
		int n = map.size();
		long avgNums = totalNums / map.size();
		double sum = 0;
		for (String key : map.keySet()) {
			int value = map.get(key);
			sum += (value - avgNums) * (value - avgNums);
		}
		return Math.sqrt(sum / n);
	}
	
	/**
	 */
	public static int hash(String data)
	{
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (int i = 0; i < data.length(); i++)
			hash = (hash ^ data.charAt(i)) * p;
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		return hash;
	}
	
	public static void main(String[] args) {
		List<String> nodes = Arrays.asList("geek00", "geek01", "geek02", "geek03", "geek04", "geek05", "geek06",
			"geek07", "geek08", "geek09");
		TestConsistHash hashCircle = new TestConsistHash();
		hashCircle.mapVirtualNode(nodes);
		Map<String, Object> keyValus = new HashMap<>();
		for (int i = 1; i <= 1000000; i++) {
			keyValus.put("key" + i, "value" + i);
		}
		hashCircle.stat(keyValus);
		hashCircle.addNode("geek10");
		hashCircle.stat(keyValus);
	}
}
