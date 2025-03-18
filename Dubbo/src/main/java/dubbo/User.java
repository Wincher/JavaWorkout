package dubbo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wincher
 * @date   27/09/2017.
 */
@Setter
@Getter
public class User implements Serializable {
	private String id;
	private String name;
	private int age;
	private String sex;
	
	public User(String id, String name, int age, String sex) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

}
