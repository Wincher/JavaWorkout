package JVM;

import java.util.Vector;

/**
 * @author wincher
 * @date   25/09/2017.
 */
public class Test03 {
	public static void main(String[] args) {
		//-Xms2m -Xmx2m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./Test03.dump
		//StackOverFlow
		
		Vector v = new Vector();
		
		for (int i = 0; i < 5; i++) {
			v.add(new Byte[1*1024*1024]);
		}
	}
}
