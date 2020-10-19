package dubbo.Consumer;

import java.util.List;

/**
 * @author wincher
 * @date   19/10/2017.
 */
public interface SampleService {
	
	String sayHello(String name);
	
	List getUsers();
}
