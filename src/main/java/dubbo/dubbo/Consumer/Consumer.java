package dubbo.dubbo.Consumer;

import dubbo.dubbo.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

/**
 * Created by wincher on 19/10/2017.
 */
public class Consumer {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"dubbo/dubbo/Consumer/sample-provider.xml"});
		context.start();
		
		
		//todo:不懂为什么要用Provider下的SampleService才可以明明是一样的类
		dubbo.dubbo.Provider.SampleService sampleService = (dubbo.dubbo.Provider.SampleService) context.getBean("sampleService");
		System.out.println(sampleService.sayHello("Tom"));
		
		List list = sampleService.getUsers();
		if (list != null && list.size() > 0) {
			for (int i = 0; i< list.size() ; i++) {
				System.out.println(((User)list.get(i)).getName());
			}
		}
	}
}
