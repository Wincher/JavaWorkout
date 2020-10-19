package dubbox.Consumer;

import dubbox.Sample;

/**
 * @author wincher
 * @date   19/10/2017.
 */
public interface SampleService {
	
	String sayHello(String name);
	
	Sample getSample();
}
