package dubbo.Provider;

import java.util.List;
import dubbo.User;

/**
 * @author wincher
 * @date   19/10/2017.
 */
public interface SampleService {
	
	String sayHello(String name);
	
	List<User> getUsers();
}
