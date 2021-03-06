package dubbox.Provider;

import dubbox.User;
import org.springframework.stereotype.Service;

/**
 * @author wincher
 * @date   24/10/2017.
 */

@Service("userService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = UserServcie.class,protocol = {"rest","dubbo"},retries = 0)
public class UserServiceImpl implements UserServcie {
	@Override
	public void testGet() {
		System.out.println("test get...");
	}
	
	@Override
	public User getUser() {
		User u = new User("123", "jonny", 19, "m");
		return u;
	}
	
	@Override
	public User getUser(Integer id) {
		return null;
	}
	
	@Override
	public User getUser(Integer id, String name) {
		return null;
	}
	
	@Override
	public void testPost() {
	
	}
	
	@Override
	public User postUser(User user) {
		return null;
	}
	
	@Override
	public User postUser(String id) {
		return null;
	}
}
