package dubbox.Provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author wincher
 * @date   19/10/2017.
 */
public class Provider {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"dubbox/Provider/sample-provider.xml"} );
		context.start();
		
		System.in.read();//模拟服务阻塞
	}
}
