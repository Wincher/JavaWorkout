package dubbo.dubbo.Provider;


import dubbo.dubbo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wincher on 19/10/2017.
 */
public class SampleServiceImpl implements SampleService {
	
	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}
	
	@Override
	public List getUsers() {
		
		List<User> users = new ArrayList<User>();
		
		users.add(new User("1","Eason", 21,"m"));
		users.add(new User("2","Linda", 23,"f"));
		return users;
	}
}
