package dubbo.dubbo.Consumer;

import java.util.List;

/**
 * Created by wincher on 19/10/2017.
 */
public interface SampleService {
	
	String sayHello(String name);
	
	List getUsers();
}
