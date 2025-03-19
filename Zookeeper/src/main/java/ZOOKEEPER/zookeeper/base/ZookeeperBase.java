package ZOOKEEPER.zookeeper.base;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author wincher
 * @date   12/10/2017.
 */
public class ZookeeperBase {
	/** zookeeper address */
	static final String CONNECT_ADDR = "CVM00:2181";
	/** session overtime  */
	static final int SESSION_OVERTIME = 5000; //ms
	/** 阻塞程序执行，用于等待zookeeper连接成功，发送成功信号  */
	static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
	static final CountDownLatch connectedSemaphore2 = new CountDownLatch(1);
	
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		
		ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OVERTIME, event -> {
			//获取事件的状态
			Watcher.Event.KeeperState keeperState = event.getState();
			Watcher.Event.EventType eventType = event.getType();
			System.out.println("Watcher triggered: " + event);
			//如果是建立连接
			if (Watcher.Event.KeeperState.SyncConnected == keeperState) {
				if (Watcher.Event.EventType.None == eventType) {
					//如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
					connectedSemaphore.countDown();
					System.out.println("zk 建立连接");
				}
			}
		});

		connectedSemaphore.await();

		try {
			String path = "/my_node";
			String data = "Hello ZooKeeper";
			zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			zk.create("/testRoot", "testRoot".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("创建节点: " + path + "，数据: " + data);
		} catch (Exception e) {
			System.err.println("ZooKeeper 操作失败: " + e.getMessage());
		}


//		create sub node
//		CreateMode设置为临时节点会在当前程序结束前一直存在，然后删除
		String ret = zk.create("/testRoot/children","children data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println(ret);
		Thread.sleep(2000);

		zk.create("/app", "c".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		//事实证明这个借口只能一层一层的创建,如果没有上面,会报错nonode...
		zk.create("/app/c1", "c1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

//		modify node data
		zk.setData("/testRoot","modify data root".getBytes(), -1);

//		is exists node
		System.out.println("==========" + zk.exists("/testRoot",false));

//		get node info
		byte[] data = zk.getData("/testRoot/children",false, null);
		System.out.println("data under /testRoot/children: " + new String(data));
		List<String> list = zk.getChildren("/testRoot",false);
		System.out.println("children of /testRoot: " + list);
		
		zk.delete("/testRoot/c1", -1,
				new AsyncCallback.VoidCallback() {
					@Override
					public void processResult(int rc, String path, Object ctx) {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							System.err.println(e.getMessage());
						}
						System.out.println(rc);
						System.out.println(path);
						System.out.println(ctx);
					}
				},"a");
		System.out.println("prepare to close...");
		zk.close();
	};
}
