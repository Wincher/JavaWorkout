package dubbox.Provider;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import dubbox.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author wincher
 * @date   23/10/2017.
 */

//要添加tomcat支持的jar包，jboss-logging.jar
@Path("/userService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
//不加public其他包无法调用，启动dubbo rest服务jboss-resteasy包调用报错
public interface UserServcie {
	
	@GET
	@Path("/testGet")
	void testGet();
	
	@GET
	@Path("/getUser")
	User getUser();
	
	User getUser(Integer id);
	
	User getUser(Integer id, String name);
	
	void testPost();
	
	User postUser(User user);
	
	User postUser(String id);
	
}
