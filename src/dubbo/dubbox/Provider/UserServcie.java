package dubbo.dubbox.Provider;

import dubbo.dubbox.User;

/**
 * Created by wincher on 23/10/2017.
 */

//不加public其他包无法调用，启动dubbo rest服务jboss-resteasy包调用报错
public interface UserServcie {
	
	void testGet();
	
	User getUser();
	
	User getUser(Integer id);
	
	User getUser(Integer id, String name);
	
	void testPost();
	
	User postUser(User user);
	
	User postUser(String id);
	
}
