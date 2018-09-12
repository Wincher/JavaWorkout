package dubbox.Consumer;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by wincher on 19/10/2017.
 */
public class Consumer {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"dubbox/Consumer/sample-consumer.xml"});
		context.start();
		
		dubbox.Provider.SampleService sampleService = (dubbox.Provider.SampleService) context.getBean("sampleService");
		System.out.println(sampleService.sayHello("Tom"));
		
		UserServcie userService = (UserServcie) context.getBean("userService");
		System.out.println(userService.getUser());
		
	}
}
