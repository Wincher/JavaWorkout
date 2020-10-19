package JVM;


import java.util.HashMap;
import java.util.Map;

/**
 * @author wincher
 * @date   25/09/2017.
 */
public class Test05 {
	
	public static void main(String[] args) {
		
		//初始化对象在eden区
		//参数：-Xmx64M -Xms64M -XX:+PrintGCDetails
		for (int i = 0; i < 5; i++) {
			byte[] b = new byte[1024*1024];
		}
		
		//测试对象进入老年代
		// -Xmx1024M -Xms1024M -XX:+PrintGCDetails -XX:MaxTenuringThreshold=15 -XX:+UseSerialGC
		
		Map<Integer, byte[]> m = new HashMap<Integer, byte[]>();
		for (int i = 0; i < 5; i++) {
			byte[] b = new byte[1024*1024];
			m.put(i, b);
		}
		
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 3000; j++) {
				byte[] b = new byte[1024*1024];
			}
		}
	}
}
