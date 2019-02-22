package kafka.redis;

import org.codehaus.jackson.map.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by wincher on 09/11/2017.
 */
public class RedisOperations implements Serializable {
	
	private static final long serialVersionUID = 1L;
	Jedis jedis = null;
	public RedisOperations(String redisIp, int port) {
		jedis = new Jedis(redisIp, port);
	}
	
	public void insert(Map<String, Object> record, String id) {
		try {
			String tmp = new ObjectMapper().writeValueAsString(record);
			jedis.set(id, tmp);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Record not persist into database :");
		}
	}
}
