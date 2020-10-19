package ZOOKEEPER.zookeeper.base;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Zookeeper watcher
 * 本类就是一个Watcher类(实现了org.apache.zookeeper.Watcher类)
 *
 * @author wincher
 * @date   16/10/2017.
 */
public class ZookeeperWatcher implements Watcher{
	/** 定义原子变量 */
	AtomicInteger seq = new AtomicInteger();
	/** define session timeout */
	private static final int SESSION_TIMEOUT = 10000;
	/** zookeeper server address */
	private static final String CONNECTION_ADDR = "192.168.0.111:2181,192.168.0.112:2181,192.168.0.113:2181";
	/** zk parent path */
	private static final String PARENT_PATH = "/p";
	/** zk child path */
	private static final String CHILDREN_PATH = "/p/c";
	/** 进入标识 */
	private static final String LOG_PREFIX_OF_MAIN = "[Main]";
	/** zk变量 */
	private ZooKeeper zk = null;
	/** 用于等待zookeeper连接建立之后，通知阻塞程序继续向下执行  */
	private CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	/**
	 *  创建zookeeper 连接
	 * @param connectAddr zk服务器地址列表
	 * @param sessionTimeout Session超时时间
	 */
	public void createConnetcion(String connectAddr, int sessionTimeout) {
		this.releaseConnection();
		try {
			//this表示把当前对象进行传递到其中去(也就是在主函数里实例化的new zookeeperwatcher() 实例对象)
			zk = new ZooKeeper(connectAddr, sessionTimeout, this);
			System.out.println(LOG_PREFIX_OF_MAIN + "开始连接zk服务器");
			connectedSemaphore.await();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void releaseConnection() {
		if (this.zk != null) {
			try {
				this.zk.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 创建节点
	 * @param path
	 * @param data
	 * @param needWatch
	 * @return
	 */
	public boolean createPath(String path, String data, boolean needWatch) {
		try {
			//设置监控(由于zookeeper的监控都是一次性的，所以每次必须设置监控)
			this.zk.exists(path, needWatch);
			System.out.println(LOG_PREFIX_OF_MAIN + "节点创建成功， Path: " +
					this.zk.create( /** 路径 */
							path,
							/** 数据 */
							data.getBytes(),
							/** 所有可见  */
							ZooDefs.Ids.OPEN_ACL_UNSAFE,
							/** 永久存储  */
							CreateMode.PERSISTENT) +
					", content: " + data);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 读取指定节点数据内容
	 * @param path
	 * @param needWatch
	 * @return
	 */
	public String readData(String path, boolean needWatch) {
		String ret = "";
		try {
			System.out.println("读取数据操作...");
			ret = new String(this.zk.getData(path, needWatch, null));
		} catch (Exception e) {
			e.printStackTrace();
			ret = "";
		}
		return ret;
	}
	
	/**
	 *  更新指定节点数据内容
	 * @param path
	 * @param data
	 * @return
	 */
	public boolean writeDate(String path, String data) {
		try {
			System.out.println(LOG_PREFIX_OF_MAIN + "更新数据成功，path: " + path + ", stat: " +
				this.zk.setData(path, data.getBytes(), -1));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 删除指定节点
	 * @param path
	 */
	public void deleteNode(String path) {
		try {
			this.zk.delete(path, -1);
			System.out.println(LOG_PREFIX_OF_MAIN + "删除节点成功，path: " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断子节点是否存在
	 * @param path
	 * @param needWatch
	 * @return
	 */
	public Stat exists(String path, boolean needWatch) {
		try {
			return this.zk.exists(path, needWatch);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取子节点
	 * @param path
	 * @param needWatch
	 * @return
	 */
	private List<String> getChildren(String path, boolean needWatch) {
		try {
			System.out.println("读取子节点操作...");
			return this.zk.getChildren(path, needWatch);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 删除所有节点
	 * @param needWatch
	 */
	public void deleteAllTestPath(boolean needWatch) {
		if (this.exists(CHILDREN_PATH, needWatch) != null) {
			this.deleteNode(CHILDREN_PATH);
		}
		if (this.exists(PARENT_PATH, needWatch) != null) {
			this.deleteNode(PARENT_PATH);
		}
	}
	
	@Override
	public void process(WatchedEvent event) {
		System.out.println("进入process ...... event = " + event);
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 连接状态
		Event.KeeperState keeperState = event.getState();
		//事件类型
		Event.EventType eventType = event.getType();
		//受影响的path
		String path = event.getPath();
		//原子对象seq记录进入process的次数
		String logPrefix = "[Watcher-" + this.seq.incrementAndGet() + "]";
		
		System.out.println(logPrefix + "收到Watcher通知");
		System.out.println(logPrefix + "连接状态:\t" + keeperState.toString());
		System.out.println(logPrefix + "事件类型:\t" + eventType.toString());
		
		if (Event.KeeperState.SyncConnected == keeperState) {
			//成功连接上ZK服务器
			if (Event.EventType.None == eventType) {
				System.out.println(logPrefix + "成功连接上zk服务器");
				connectedSemaphore.countDown();
			}//创建节点
			else if (Event.EventType.NodeCreated == eventType) {
				System.out.println(logPrefix + "节点创建");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}//更多节点
			else if (Event.EventType.NodeDataChanged == eventType) {
				System.out.println(logPrefix + "节点数据更新");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}//更新子节点
			else if (Event.EventType.NodeChildrenChanged == eventType) {
				System.out.println(logPrefix + "子节点变更");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}//删除节点
			else if (Event.EventType.NodeDeleted == eventType) {
				System.out.println(logPrefix + "节点: " + path + "被删除");
			} else ;
		} else if (Event.KeeperState.Disconnected == keeperState) {
			System.out.println(logPrefix + "与ZK服务器断开连接");
		} else if (Event.KeeperState.AuthFailed == keeperState) {
			System.out.println(logPrefix + "权限检查失败");
		} else if (Event.KeeperState.Expired == keeperState) {
			System.out.println(logPrefix + "会话失效");
		} else ;
		System.out.println("------------------------");
	}
	
	/**
	 * 方法名称：测试zookeeper监控
	 * 概要说明：主要测试watch功能
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		
		//建立watcher //当前客户端可以称为一个watcher 观察者角色
		ZookeeperWatcher zkWatch = new ZookeeperWatcher();
		//创建连接
		zkWatch.createConnetcion(CONNECTION_ADDR, SESSION_TIMEOUT);
		System.out.println(zkWatch.zk.toString());
		
		Thread.sleep(1000);
		
		//清理节点
		zkWatch.deleteAllTestPath(false);
		
		//----------------- 第一步：创建父节点 --------------------
		if (zkWatch.createPath(PARENT_PATH, System.currentTimeMillis() + "", true)) {
			Thread.sleep(1000);
			//----------------- 第二步：读取节点p 和读取 p节点下的子节点 getChildren的区别--------------------
			//读取数据
			zkWatch.readData(PARENT_PATH, true);
			//读取子节点 (监控childrenNodeChange事件)
			zkWatch.getChildren(PARENT_PATH, true);
			//更新数据
			zkWatch.writeDate(PARENT_PATH, System.currentTimeMillis() + "");
			
			Thread.sleep(1000);
			//创建子节点
			zkWatch.createPath(CHILDREN_PATH, System.currentTimeMillis() + "", true);
			
			//----------------- 第三步：建立子节点的触发 --------------------
//			zkWatch.createPath(CHILDREN_PATH + "/c1", System.currentTimeMillis() + "", true);
//			zkWatch.createPath(CHILDREN_PATH + "/c1/c2", System.currentTimeMillis() + "", true);
			
			//----------------- 第四步：更新子节点数据的触发 --------------------
			//在进行修改之前，我们需要watch一下这个节点：
			Thread.sleep(1000);
			zkWatch.readData(CHILDREN_PATH, true);
			zkWatch.writeDate(CHILDREN_PATH, System.currentTimeMillis() + "");
		}
		
		Thread.sleep(10000);
		//清理节点
		zkWatch.deleteAllTestPath(false);
		
		Thread.sleep(10000);
		zkWatch.releaseConnection();
		
	}
}
