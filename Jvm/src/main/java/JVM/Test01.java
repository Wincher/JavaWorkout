package JVM;

/**
 * @author wincher
 * @date   24/09/2017.
 */
public class Test01 {
	public static void main(String[] args) {
		// -XX:+UseCompressedOops -XX:+UseLargePagesIndividualAllocation
		
		// -Xms5m -Xmx20m -XX:+PrintGCDetails
		// -XX:+UseSerialGC -XX:+PrintCommandLineFlag
		
		//check gc information
		System.out.println("max memory: " + Runtime.getRuntime().maxMemory());
		System.out.println("free memory: " + Runtime.getRuntime().freeMemory());
		System.out.println("total memory: " + Runtime.getRuntime().totalMemory());
		
		
		byte[] b1 = new byte[1*1024*1024];
		
		System.out.println("allocate 1M");
		
		System.out.println("max memory: " + Runtime.getRuntime().maxMemory());
		System.out.println("free memory: " + Runtime.getRuntime().freeMemory());
		System.out.println("total memory: " + Runtime.getRuntime().totalMemory());
		
		byte[] b2 = new byte[4*1024*1024];
		
		System.out.println("allocate 4M");
		System.out.println("max memory: " + Runtime.getRuntime().maxMemory());
		System.out.println("free memory: " + Runtime.getRuntime().freeMemory());
		System.out.println("total memory: " + Runtime.getRuntime().totalMemory());
		
		int a = 0x00000000fa0a0000;
		int b = 0x00000000fa801000;
		
		System.out.println("result is:" + (b-a)/1024);
	}
}
