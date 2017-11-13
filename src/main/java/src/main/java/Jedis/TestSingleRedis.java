package Jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.*;

import java.util.*;

/**
 * Created by wincher on 27/09/2017.
 */
public class TestSingleRedis {
	//单独连接一台redis服务器
	private static ShardedJedis shard;
	//主从，哨兵下使用shard，shard只是逻辑上的分片，并不是物理上的分片
	private static Jedis jedis;
	//连接池 (存放多个jedis连接)
	private static ShardedJedisPool pool;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//单个节点
		jedis = new Jedis("192.168.0.111", 6379);
		
		//分片
		List<JedisShardInfo> shards = Arrays.asList(
				new JedisShardInfo("192.168.0.111", 6379));
		shard = new ShardedJedis(shards);
		
		GenericObjectPoolConfig goConfig = new GenericObjectPoolConfig();
		goConfig.setMaxTotal(100);
		goConfig.setMaxIdle(20);
		goConfig.setMaxWaitMillis(-1);
		pool = new ShardedJedisPool(goConfig, shards);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		jedis.disconnect();
		shard.disconnect();
		pool.destroy();
	}
	
	@Test
	public void testString() {
		//添加数据
		jedis.set("name", "wicnehr");
		System.out.println(jedis.get("name"));
		
		jedis.append("name", " is my idol!");
		System.out.println(jedis.get("name"));
		
		jedis.del("name");
		System.out.println(jedis.get("name"));
		
		jedis.mset("name","wincher", "age", "27", "sex", "male");
		jedis.incr("age");
		
		System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("sex"));
	}
	
	@Test
	public void testMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "hehe");
		map.put("age", "12");
		map.put("sex", "female");
		jedis.hmset("user", map);
		//  取出user重的name，执行姐多：[minxr]-->注意结果是一个泛型的list
		//第一个参数是存入redis中map对象的key，后面跟的是放入map重的对象的key，后面的key可以跟多个，是可变参数
		List<String> rsmap = jedis.hmget("user", "name","age", "sex");
		System.out.println(rsmap);
		
		jedis.hdel("user", "age");
		System.out.println(jedis.hmget("user","age")); //因为删除返回null
		System.out.println(jedis.hlen("user"));  //返回key为user的键中存放的值的个数2
		System.out.println(jedis.exists("user")); //是否存在key为user的记录 返回true
		System.out.println(jedis.hkeys("user"));//返回map对象中所有的key
		System.out.println(jedis.hvals("suer")); //返回map对象中所有value
		
		Iterator<String> iterator = jedis.hkeys("user").iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			System.out.println(key + " : " + jedis.hmget("user", key));
		}
	}
	
	@Test
	public void testList() {
		//开始前移除所有内容
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0 , -1));
		//先向key java framework中存放三条数据
		jedis.lpush("java framework","spring");
		jedis.lpush("java framework","struts");
		jedis.lpush("java framework","hibernate");
		
		//再取出所有数据jedis.lrange是按范围取出，
		//第一个是key，第二个是起始位置，第三个是结束为止，jedis.llen获取长度-1表示取得所有
		System.out.println(jedis.lrange("java framework", 0 , -1));
		
		jedis.del("java framework");
		jedis.rpush("java framework", "spring");
		jedis.rpush("java framework", "struts");
		jedis.rpush("java framework", "hibernate");
		System.out.println(jedis.lrange("java framework",0 ,-1));
		
	}
	
	@Test
	public void testSet() {
		jedis.sadd("userSet","wincher");
		jedis.sadd("userSet","john");
		jedis.sadd("userSet","smith");
		jedis.sadd("userSet","linda");
		jedis.sadd("userSet","jane");
		
		jedis.srem("userSet","linda");
		System.out.println(jedis.smembers("userSet"));
		System.out.println(jedis.sismember("userSet", "linda"));
		System.out.println(jedis.srandmember("userSet"));
		System.out.println(jedis.scard("userSet"));
	}
	
	@Test
	public void testRLpush() {
		//jedis 排序
		//注意，此处rpush和lpush从表现上来看是List的曹错，是一个双向链表
		
		jedis.del("a");
		
		jedis.rpush("a", "1");
		jedis.lpush("a", "8");
		jedis.lpush("a", "5");
		jedis.lpush("a", "3");
		
		System.out.println(jedis.lrange("a", 0, -1));
		System.out.println(jedis.sort("a"));
		System.out.println(jedis.lrange("a", 0, -1));
	}
	
	@Test
	public void testTrans() {
		long start = System.currentTimeMillis();
		Transaction tx = jedis.multi();
		for (int i = 0; i < 1000; i++) {
			tx.set("k" + i, "v" + i);
		}
		List<Object> results = tx.exec();
		System.out.println(results);
		long end = System.currentTimeMillis();
		System.out.println("Transaction set: " + ((end - start)/1000.0) + "seconds");
		
	}
	//todo 与multi相比不保证原子性，一次性发送，实际上貌似multi出错不会回滚，待考证
	@Test
	public void testPipelined() {
		Pipeline pipeline = jedis.pipelined();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			pipeline.set("pk" + i, "pv" + i);
		}
		List<Object> results = pipeline.syncAndReturnAll();
		System.out.println(results);
		long end = System.currentTimeMillis();
		System.out.println("Pipeline set: " + ((end - start)/1000.0) + "seconds");
	}
	
	@Test
	public void testShard() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			String result = shard.set("shard" + i,"n" + i);
			System.out.println(result);
		}
		long end = System.currentTimeMillis();
		System.out.println("Shard set: " + ((end - start)/1000.0) + "seconds");
	}
	
	@Test
	public void testShardPipelined() {
		ShardedJedisPipeline pipeline = shard.pipelined();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			pipeline.hset("shard piped", "k" + i, "v" + i);
		}
		List<Object> result = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("ShardPipelined set: " + ((end - start)/1000.0) + "seconds");
	}
	
	@Test
	public void testShardPool() {
		ShardedJedis sj = pool.getResource();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			String result = sj.set("sj" + i,"n" + i);
			System.out.println(result);
		}
		long end = System.currentTimeMillis();
		pool.returnResourceObject(sj);
		System.out.println("ShardPool set: " + ((end - start)/1000.0) + "seconds");
	}
}
