package dubbox;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wincher
 * @date   23/10/2017.
 */
public class Sample implements Serializable {
	private String name;
	private int age;
	private Map<String, Integer> map;
	public Sample(){};
	
	public Sample(String name, int age, Map<String, Integer> map) {
		this.name = name;
		this.age = age;
		this.map = map;
	}
}
