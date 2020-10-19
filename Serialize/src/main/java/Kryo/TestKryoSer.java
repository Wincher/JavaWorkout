package Kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wincher
 * @date   19/09/2017.
 */
public class TestKryoSer {
	private static final String FILE_DIR = "";
	
	public static void main(String[] args) throws FileNotFoundException {
		long start = System.currentTimeMillis();
		setSerializableObject();
		System.out.println("java kryo writeObject time:" + (System.currentTimeMillis() - start) + "ms");
		start = System.currentTimeMillis();
		getSerializableObject();
		System.out.println("java kryo writeObject time:" + (System.currentTimeMillis() - start) + "ms");
	}
	
	public static void setSerializableObject() throws FileNotFoundException {
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
		//Todo 待解决
		//kryo.setInstantiatorStrategy((InstantiatorStrategy)new StdInstantiatorStrategy());
		kryo.register(Entity.class);
		Output output = new Output(new FileOutputStream(FILE_DIR));
		for (int i = 0; i < 100000; i++) {
			Map<String, Integer> map = new HashMap<String, Integer>(2);
			map.put("zhang0",i);
			map.put("zhang1",i);
			kryo.writeObject(output, new Entity("zhang" + i, (i+1),map));
		}
		output.flush();
		output.clear();
	}
	public static void getSerializableObject() {
		
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
		//Todo 待解决
		//kryo.setInstantiatorStrategy((InstantiatorStrategy)new StdInstantiatorStrategy());
		Input input;
		try {
			input = new Input(new FileInputStream(FILE_DIR));
			Entity entity = null;
			while ((entity = kryo.readObject(input,Entity.class)) != null) {
				System.out.println(entity.getName() + " : " + entity.getAge());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
