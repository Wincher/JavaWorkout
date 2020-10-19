package JVM;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wincher
 * @date   25/09/2017.
 */
public class Test06 {
	public static void main(String[] args) {
		
		//指定对象超过指定大小之后直接晋升老年代
		//参数1
		//-Xmx30M -Xms30M -XX:+UseSerialGC -XX:+PrintGCDetails -XX:PretenureSizeThreshold=1000
		//这种现象成因：虚拟机对于体积不大的对象 会优先把数据分配到TLAB区域中，因此就失去了在老年代分配的机会
		//参数2
		//-Xmx30M -Xms30M -XX:+UseSerialGC -XX:+PrintGCDetails -XX:PretenureSizeThreshold=1000 -XX:-UseTLAB
		//要注意TLAB区域优先分配空间
		
		Map<Integer, byte[]> m = new HashMap<Integer, byte[]>();
		for (int i = 0; i < 5 * 1024; i++) {
			byte[] b = new byte[1024];
			m.put(i, b);
			
		}
		
	}
}
