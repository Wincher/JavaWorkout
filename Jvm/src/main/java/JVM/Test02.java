package JVM;

/**
 * @author wincher
 * @date   24/09/2017.
 */
public class Test02 {
	public static void main(String[] args) {
		//第一次配置 eden 2 = from 1 + to 1
		//-Xms20m -Xmx20m -Xmn1m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC
		
		//第二次配置 eden 2 = from 1 + to 1
		//-Xms20m -Xmx20m -Xmn7m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC
		
		//第三次配置 eden 2 = from 1 + to 1
		//-Xms20m -Xmx20m -XX:NewRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC
		
		byte[] b = null;
		//allocate 10M space
		for (int i = 0; i < 10; i++) {
			b = new byte[10*1024*1024];
		}
	}
}
