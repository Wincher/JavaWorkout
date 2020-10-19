package ZOOKEEPER.zookeeper.cluster;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @author wincher
 * @date   17/10/2017.
 */
public class ZKWatcher implements Watcher {
	
	/** zk变量 */
	private ZooKeeper zk = null;
	/** 父节点path */
	static final String PARENT_PATH = "/super";
	/** 信号量设置， 用于等待zookeeper连接建立之后，通知阻塞程序继续向下执行 */
	private CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	private List<String> cowaList = new ArrayList<String>();
	/** zookeeper服务器地址*/
	public static final String CONNECTION_ADDR = "192.168.0.111:2181,192.168.0.112:2181,192.168.0.113:2181";
	/** 定义session失效时间 */
	public static final int SESSION_TIMEOUT = 30000;
	
	public ZKWatcher() throws InterruptedException, IOException {
		this.zk = new ZooKeeper(CONNECTION_ADDR, SESSION_TIMEOUT, this);
		System.out.println("start connect Zookeeper server");
		connectedSemaphore.await();
	}
	
	@Override
	public void process(WatchedEvent event) {
		
		// 连接状态
		Event.KeeperState keeperState = event.getState();
		//事件类型
		Event.EventType eventType = event.getType();
		//受影响的path
		String path = event.getPath();
		System.out.println("受影响的path：" + path);
		
		if (Event.KeeperState.SyncConnected == keeperState) {
			//成功连接上ZK服务器
			if (Event.EventType.None == eventType) {
				System.out.println("成功连接上zk服务器");
				connectedSemaphore.countDown();
				try {
					if (this.zk.exists(PARENT_PATH, false) == null) {
						this.zk.create(PARENT_PATH, "root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}
					List<String> paths = this.zk.getChildren(PARENT_PATH, true);
					for (String p : paths) {
						System.out.println(p);
						this.zk.exists(PARENT_PATH + "/" + p, true);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (KeeperException e) {
					e.printStackTrace();
				}
			}//创建节点
			else if (Event.EventType.NodeCreated == eventType) {
				System.out.println("节点创建");
				try {
					this.zk.exists(path, true);
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e ) {
					e.printStackTrace();
				}
			}//更多节点
			else if (Event.EventType.NodeDataChanged == eventType) {
				System.out.println("节点数据更新");
				try {
					this.zk.exists(path, true);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (KeeperException e) {
					e.printStackTrace();
				}
			}//更新子节点
			else if (Event.EventType.NodeChildrenChanged == eventType) {
				System.out.println("子节点变更");
				try {
					List<String> paths = this.zk.getChildren(path, true);
					if (paths.size() >= cowaList.size()) {
						paths.removeAll(cowaList);
						for (String p : paths) {
							this.zk.exists(path + "/" + p, true);
							System.out.println("新增子节点： " + path + "/" + p);
						}
						cowaList.addAll(paths);
					} else {
						cowaList = paths;
					}
					System.out.println("cowaList: " + cowaList.toString());
					System.out.println("Paths: " + path.toString());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (KeeperException e) {
					e.printStackTrace();
				}
			}//删除节点
			else if (Event.EventType.NodeDeleted == eventType) {
				System.out.println("节点: " + path + "被删除");
				try {
					this.zk.exists(path, true);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (KeeperException e) {
					e.printStackTrace();
				}
			} else ;
		} else if (Event.KeeperState.Disconnected == keeperState) {
			System.out.println("与ZK服务器断开连接");
		} else if (Event.KeeperState.AuthFailed == keeperState) {
			System.out.println("权限检查失败");
		} else if (Event.KeeperState.Expired == keeperState) {
			System.out.println("会话失效");
		} else ;
	}
}
