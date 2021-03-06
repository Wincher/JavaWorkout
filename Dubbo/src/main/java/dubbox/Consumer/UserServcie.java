package dubbox.Consumer;

import dubbox.User;

/**
 * @author wincher
 * @date   23/10/2017.
 */
interface UserServcie {
	
	void testGet();
	
	User getUser();
	
	User getUser(Integer id);
	
	User getUser(Integer id, String name);
	
	void testPost();
	
	User postUser(User user);
	
	User postUser(String id);
	
}
