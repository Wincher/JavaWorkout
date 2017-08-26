package MultiThread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by wincher on 26/08/2017.
 * 同步类容器使用
 */
public class MultiThread13 {
	public static void main(String[] args) {
		useConcurrentMap();
		useCopyOnWrite();
	}
	
	private static void useCopyOnWrite() {
		CopyOnWriteArrayList<String> cwal = new CopyOnWriteArrayList<String>();
		CopyOnWriteArraySet<String> cwas = new CopyOnWriteArraySet<String>();
		
		cwal.add("hello");
		System.out.println(cwal);
	}
	
	private static void useConcurrentMap() {
		ConcurrentHashMap<String, Object> chm = new ConcurrentHashMap<String,Object>();
		chm.put("k1","v1");
		chm.put("k2","v2");
		chm.put("k3","v3");
		chm.put("k4","v4");
		chm.put("k4","v5");
		//复合操作如果value存在不会存入，put则会覆盖原有value
		chm.putIfAbsent("k4","v6");
		
		for (Map.Entry<String, Object> me : chm.entrySet()) {
			System.out.println("key: " + me.getKey() + ", value: " + me.getValue());
		}
	}
}
