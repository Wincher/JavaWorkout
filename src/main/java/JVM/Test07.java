package JVM;

/**
 * Created by wincher on 25/09/2017.
 */
public class Test07 {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		//TALB分配
		//参数：-XX:+UseTLAB -XX:+PrintTLAB -XX:+PrintGC -XX:TLABSize=102400 -XX:-ResizeTLAB
		// -XX:TLABRefillWasteFraction=100 -XX:-DoEscapeAnalysis -server
		
		for (int i = 0; i < 1000000000; i++) {
			alloc();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println(end - start);
	}
	
	private static void alloc() {
		byte[] b = new byte[2];
	}
}
