package ZOOKEEPER.zkclient.base;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * Created by wincher on 17/10/2017.
 */
public class ZkClientBase {
	/** zookeeper address */
	static final String CONNECT_ADDR = "192.168.0.111:2181,192.168.0.112:2181,192.168.0.113:2181";
	/** session超时时间 */
	static final int SESSION_OUTTIME = 5000;
	
	public static void main(String[] args) throws InterruptedException {
		ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), 10000);
		
		//1 create and delete method
//		zkc.create("/root", "root data".getBytes(), CreateMode.PERSISTENT);
		zkc.createEphemeral("/temp");
		zkc.createPersistent("/super/c1", true);
		Thread.sleep(1000);
		zkc.deleteRecursive("/super");
		
		//2设置path和data 并且读取子节点和每个节点的内容
		zkc.createPersistent("/super", "1234");
		zkc.createPersistent("/super/c1", "c1内容");
		zkc.createPersistent("/super/c2", "c2内容");
//		zkc.deleteRecursive("/super");
		List<String> list = zkc.getChildren("/super");
		for (String p : list) {
			System.out.println(p);
			String rp = "/super/" + p;
			String data = zkc.readData(rp);
			System.out.println("节点为：" + rp + ", 内容为：" + data);
		}
		
		//3更新和判断节点是否存在
		zkc.writeData("/super/c1", "新内容");
		System.out.println(zkc.exists("/super/c1"));
		
		//4 递归删除/super 内容
		zkc.deleteRecursive("/super");
		
		zkc.close();
		
		
		
	}
}
