package Jedis;

import Jedis.entity.User;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by wincher on 27/09/2017.
 */
public class TestRedis {
	
	private static Jedis j;
	
	
	//指定业务，查询业务：SYS_USER_AGE_25
	//指定业务 查询业务：SYS_USER_SEX_M
	//指定业务 查询业务：SYS_USER_SEX_F
	
	final static String SUT = "SYS_USER_TABLE";
	final static String SUA25 = "SYS_USER_AGE_25";
	final static String SUSMA25 = "SYS_USER_SEX_M_AGE_25";
	final static String SUSFA25 = "SYS_USER_SEX_F_AGE_25";
	
	@BeforeClass
	public static void before() {
		j = new Jedis("192.168.1.23", 16379);
	}
	
	@Test
	public void test() {
		
		//User对象 数据量很大，查询很频繁,需要把user表里的数据都放入缓存中去
		
		//放入操作
		// UUID
		Map<String, String> map = new HashMap<String, String>();
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			String uuid = UUID.randomUUID().toString();
			User u = new User(uuid,
					"name" + i,
					i%60,
					random.nextBoolean()?"m":"f");
			
			map.put(uuid, FastJsonConvert.convetObjectToJson(u));
		}
		
		j.hmset(SUT,map);
	}
	
	@Test
	public void test2() {
		//想象一下存了千万级的数据在redis中
		//select * from user where age = 25
		//select * from user where age = 25 and sex = "m"
		//这就需要多种集合配合使用  hash和set同时使用
		//将所有业务数据存入hash总表，然后对符合业务查寻的数据的id存入set，
		// 然后通过smembers获取的id再通过hmget获取到所有符合的数据
		
		
		Map<String, String> map = new HashMap<String, String>();
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			String uuid = UUID.randomUUID().toString();
			User u = new User(uuid,
					"name" + i,
					i%60,
					random.nextBoolean()?"m":"f");
			
			map.put(uuid, FastJsonConvert.convetObjectToJson(u));
			if (i%60 == 25) {
				j.sadd(SUA25, uuid);
				if ( "m".equals(u.getSex()) ) {
					j.sadd(SUSMA25, uuid);
				} else if ("f".equals(u.getSex()) ) {
					j.sadd(SUSFA25, uuid);
				}
			}
			
		}
		
		j.hmset("SUT",map);
	}
	
	@Test
	public void getResult() {
		Set<String> user_ages = j.smembers(SUA25);
		//可以取出业务的交集数据
		j.sinter(SUA25,SUSFA25);
		System.out.println(user_ages);
		String[] temp = user_ages.toString().replace("[", "").replace("]", "").split(", ");
		List<String> results = j.hmget(SUT, temp);
		for (String result : results) {
			User u = FastJsonConvert.convertJSONToObject(result, User.class);
			System.out.println(u);
		}
	}
}