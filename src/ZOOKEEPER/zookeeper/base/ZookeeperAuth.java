package ZOOKEEPER.zookeeper.base;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wincher on 17/10/2017.
 */
public class ZookeeperAuth implements Watcher {
	/** 连接地址 */
	final static String CONNECT_ADDR = "192.168.0.111:2181";
	/** 测试路径 */
	final static String PATH = "/testAuth";
	final static String PATH_DEL = "/testAuth/delNode";
	/** 认证类型 */
	final static String authentication_type = "digest";
	/** 认证正确方法 */
	final static String correctAuthenticcation = "123456";
	/** 认证错误方法 */
	final static String badAuthentication = "654321";
	
	static ZooKeeper zk = null;
	/** 计时器 */
	AtomicInteger seq = new AtomicInteger();
	/** 标识 */
	private static final String LOG_PREFIX_OF_MAIN = "[MAIN]";
	
	private CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	@Override
	public void process(WatchedEvent event) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (null == event) {
			return;
		}
		//连接状态
		Event.KeeperState keeperState = event.getState();
		//连接类型
		Event.EventType eventType = event.getType();
		//受影响的path
		String path = event.getPath();
		
		String logPrefix = "[Watcher-" + this.seq.incrementAndGet() + "]";
		
		System.out.println(logPrefix + "收到Watcher通知");
		System.out.println(logPrefix + "连接状态：\t" + keeperState.toString());
		System.out.println(logPrefix + "事件类型：\t" + eventType.toString());
		
		if (Event.KeeperState.SyncConnected == keeperState) {
			//成功连接上ZK服务器
			if (Event.EventType.None == eventType) {
				System.out.println(logPrefix + "成功连接上ZK服务器");
				connectedSemaphore.countDown();
			}
		} else if (Event.KeeperState.Disconnected == keeperState) {
			System.out.println(logPrefix + "与ZK服务器断开连接");
		} else if (Event.KeeperState.AuthFailed == keeperState) {
			System.out.println(logPrefix + "权限检查失败");
		} else if (Event.KeeperState.Expired == keeperState) {
			System.out.println(logPrefix + "会话失效");
		}
		System.out.println("---------------------------");
		
	}
	
	/**
	 *  创建zookeeper 连接
	 * @param connectAddr zk服务器地址列表
	 * @param sessionTimeout Session超时时间
	 */
	public void createConnection(String connectAddr, int sessionTimeout) {
		this.releaseConnection();
		try {
			zk = new ZooKeeper(connectAddr, sessionTimeout, this);
			//添加节点授权
			zk.addAuthInfo(authentication_type, correctAuthenticcation.getBytes());
			System.out.println(LOG_PREFIX_OF_MAIN + "开始连接zk服务器");
			connectedSemaphore.await();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭zk连接
	 */
	private void releaseConnection() {
		if (this.zk != null) {
			try {
				this.zk.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ZookeeperAuth testAuth = new ZookeeperAuth();
		testAuth.createConnection(CONNECT_ADDR, 2000);
		List<ACL> acls = new ArrayList<ACL>(1);
		for (ACL ids_acl : ZooDefs.Ids.CREATOR_ALL_ACL) {
			acls.add(ids_acl);
		}
		
		try {
			zk.create(PATH, "init content".getBytes(), acls, CreateMode.PERSISTENT);
			System.out.println("使用授权key：" + correctAuthenticcation + "创建节点：" + PATH + ", 初始内容是：" +
					"init content");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
		try {
			zk.create(PATH_DEL, "will be deleted!".getBytes(), acls, CreateMode.PERSISTENT);
			System.out.println("使用授权key：" + correctAuthenticcation + "创建节点：" + PATH_DEL + ", 初始内容是：" +
					"init content");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
		
		//获取数据
		getDataByNoAuthentication();
		getDataByBadAuthentication();
		getDataByCorrectAuthentication();
		//更新数据
		updateDataByNoAuthentication();
		updateDataByBadAuthentication();
		updateDataByCorrectAuthentication();
		//删除数据
		deleteDataByNoAuthentication();
		deleteDataByBadAuthentication();
		deleteDataByCorrectAuthentication();
		
		Thread.sleep(1000);
		
		deleteParent();
		//释放连接
		testAuth.releaseConnection();
	}
	
	private static void deleteParent() {
	
	}
	
	private static void deleteDataByCorrectAuthentication() {
	
	}
	
	private static void deleteDataByBadAuthentication() {
	
	}
	
	private static void deleteDataByNoAuthentication() {
	
	}
	
	private static void updateDataByCorrectAuthentication() {
	
	}
	
	private static void updateDataByBadAuthentication() {
	
	}
	
	private static void updateDataByNoAuthentication() {
	
	}
	
	private static void getDataByCorrectAuthentication() {
		String prefix = "[使用正确的授权信息]";
		try {
			System.out.println(prefix + "获取数据：" + PATH);
			
			System.out.println(prefix + "成功获取数据：" + zk.getData(PATH, false, null));
		} catch (Exception e) {
			System.err.println(prefix + "获取数据失败， 原因：" + e.getMessage());
		}
	}
	
	private static void getDataByBadAuthentication() {
		String prefix = "[使用错误的授权信息]";
		try {
			ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, 2000, null);
			zk.addAuthInfo(authentication_type, badAuthentication.getBytes());
			Thread.sleep(2000);
			System.out.println(prefix + "获取数据：" + PATH);
			System.out.println(prefix + "成功获取数据：" + zk.getData(PATH, false, null));
		} catch (Exception e) {
			System.err.println(prefix + "获取数据失败， 原因：" + e.getMessage());
		}
	}
	
	private static void getDataByNoAuthentication() {
		String prefix = "[不使用任何授权信息]";
		try {
			System.out.println(prefix + "获取数据：" + PATH);
			ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, 2000, null);
			Thread.sleep(2000);
			System.out.println(prefix + "成功获取数据：" + zk.getData(PATH, false, null));
		} catch (Exception e) {
			System.err.println(prefix + "获取数据失败， 原因：" + e.getMessage());
		}
	}
}
