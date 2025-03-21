package dubbo.Provider;


import dubbo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wincher
 * @date   19/10/2017.
 */
public class SampleServiceImpl implements SampleService {
	
	@Override
	public String sayHello(String name) {
		System.out.println("sayHello :" + name);
		return "Hello " + name;
	}
	
	@Override
	public List<User> getUsers() {
		System.out.println("getUsers start");
		List<User> users = new ArrayList<User>();
		users.add(new User("1","Eason", 21,"m"));
		users.add(new User("2","Linda", 23,"f"));
		return users;
	}
}
