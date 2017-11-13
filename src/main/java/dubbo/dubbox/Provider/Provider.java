package dubbo.dubbox.Provider;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by wincher on 19/10/2017.
 */
public class Provider {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"dubbo/dubbox/Provider/sample-provider.xml"} );
		context.start();
		
		System.in.read();//模拟服务阻塞
	}
}
