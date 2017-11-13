package Kryo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wincher on 19/09/2017.
 */
public class testJavaSer {
	
	private static final String FILE_LOCATE = "";
	
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		setSerializableObject();
		System.out.println("java Serializable writeObject time:" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		getSerializableObject();
		System.out.println("java Serializable writeObject time:" + (System.currentTimeMillis() - start));
	}
	
	private static void setSerializableObject() throws IOException {
		FileOutputStream fo = new FileOutputStream(FILE_LOCATE);
		ObjectOutputStream so = new ObjectOutputStream(fo);
		
		for (int i = 0; i < 100000; i++) {
			Map<String, Integer> map = new HashMap<String, Integer>(2);
			map.put("zhang0",i);
			map.put("zhang1",i);
			so.writeObject(new Entity("zhang" + i, (i+1),map));
		}
		so.flush();
		so.close();
	}
	
	public static void getSerializableObject() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(FILE_LOCATE);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Entity entity = null;
			while ((entity = (Entity)ois.readObject()) != null ) {
				System.out.println(entity.getAge() + " : " + entity.getName());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
