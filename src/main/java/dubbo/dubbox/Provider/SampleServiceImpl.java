package dubbo.dubbox.Provider;


import dubbo.dubbox.Sample;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wincher on 19/10/2017.
 */
@Service("simpleService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = dubbo.dubbox.Provider.SampleService.class, protocol = {"dubbo"}, retries = 0)
public class SampleServiceImpl implements SampleService {
	
	@Override
	public String sayHello(String name) {
		System.out.println("----------------------AAA");
		return "Hello " + name;
	}
	
	@Override
	public Sample getSample() {
		Map<String, Integer> map = new HashMap<String, Integer>(2);
		map.put("zhang0", 1);
		map.put("zhang2", 1);
		return new Sample("zhang3", 21, map);
	}
}
