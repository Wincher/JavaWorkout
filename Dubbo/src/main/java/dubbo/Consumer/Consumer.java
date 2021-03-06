package dubbo.Consumer;

import dubbo.Provider.SampleService;
import dubbo.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

/**
 * @author wincher
 * @date   19/10/2017.
 */
public class Consumer {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"dubbo/Consumer/sample-consumer.xml"});
		context.start();
		
		SampleService sampleService = (SampleService) context.getBean("sampleService");
		System.out.println(sampleService.sayHello("Tom"));
		
		List list = sampleService.getUsers();
		if (list != null && list.size() > 0) {
			for (int i = 0; i< list.size() ; i++) {
				System.out.println(((User)list.get(i)).getName());
			}
		}
	}
}
