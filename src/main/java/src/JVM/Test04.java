package JVM;

/**
 * Created by wincher on 25/09/2017.
 */
public class Test04 {
	// -Xss1m
	// -Xss5m
	
	//栈调用深度
	private static int count;
	
	public static void recursion() {
		count++;
		recursion();
	}
	
	public static void main(String[] args) {
		try {
			recursion();
		} catch (Throwable t) {
			System.out.println("调用最大深度: " + count );
			t.printStackTrace();
		}
	}
}
