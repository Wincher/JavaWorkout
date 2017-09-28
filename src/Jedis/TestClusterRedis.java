package Jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wincher on 27/09/2017.
 */
public class TestClusterRedis {
	
	public static void main(String[] args) {
		Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		jedisClusterNode.add(new HostAndPort("192.168.0.111", 6379));
		jedisClusterNode.add(new HostAndPort("192.168.0.112", 6379));
		jedisClusterNode.add(new HostAndPort("192.168.0.113", 6379));
		jedisClusterNode.add(new HostAndPort("192.168.0.114", 6379));
		jedisClusterNode.add(new HostAndPort("192.168.0.115", 6379));
		jedisClusterNode.add(new HostAndPort("192.168.0.116", 6379));
		
		JedisPoolConfig cfg = new JedisPoolConfig();
		cfg.setMaxTotal(100);
		cfg.setMaxIdle(20);
		cfg.setMaxWaitMillis(-1);
		cfg.setTestOnBorrow(true);
		JedisCluster jc = new JedisCluster(jedisClusterNode, 6000, 100,cfg);
		
		System.out.println(jc.set("age", "20"));
		System.out.println(jc.set("sex","f"));
		System.out.println(jc.get("name"));
		System.out.println(jc.get("name"));
		System.out.println(jc.get("name"));
		System.out.println(jc.get("name"));
		System.out.println(jc.get("name"));
		System.out.println(jc.get("name"));
		System.out.println(jc.get("age"));
		System.out.println(jc.get("sex"));
		
		jc.close();
		
		
	}
	
}
