package dubbo.dubbox.Consumer;

import dubbo.dubbox.Sample;

/**
 * Created by wincher on 19/10/2017.
 */
public interface SampleService {
	
	String sayHello(String name);
	
	Sample getSample();
}
