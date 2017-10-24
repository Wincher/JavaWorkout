package dubbo.dubbox.Provider;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import dubbo.dubbox.User;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by wincher on 24/10/2017.
 */

//要添加tomcat支持的jar包，jboss-logging.jar
@Path("/userService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Service("userService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = UserServcie.class,protocol = {"rest","dubbo"},retries = 0)
public class UserServiceImpl implements UserServcie {
	@GET
	@Path("/testGet")
	@Override
	public void testGet() {
		System.out.println("test get...");
	}
	
	@GET
	@Path("/getUser")
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
