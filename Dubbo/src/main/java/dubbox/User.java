package dubbox;

import java.io.Serializable;

/**
 * Created by wincher on 27/09/2017.
 */
public class User implements Serializable {
	private String id;
	private String name;
	
	public User(String id, String name, int age, String sex) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
