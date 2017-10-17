package ZOOKEEPER.zookeeper.base;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wincher on 12/10/2017.
 */
public class ZookeeperBase {
	/** zookeeper address */
	static final String CONNETC_ADDR = "192.168.0.111:2181,192.168.0.112:2181,192.168.0.113:2181,";
	/** session outtime  */
	static final int SESSION_OUTTIME = 5000; //ms
	/** 阻塞程序执行，用于等待zookeeper连接成功，发送成功信号  */
	static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
	static final CountDownLatch connectedSemaphore2 = new CountDownLatch(1);
	
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		
		ZooKeeper zk = new ZooKeeper(CONNETC_ADDR, SESSION_OUTTIME, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				//获取事件的状态
				Event.KeeperState keeperState = event.getState();
				Event.EventType eventType = event.getType();
				//如果是建立连接
				if (Event.KeeperState.SyncConnected == keeperState) {
					if (Event.EventType.None == eventType) {
						//如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
						connectedSemaphore.countDown();
						System.out.println("zk 建立连接");
					}
				}
			}
		});
		
		//进行阻塞
		connectedSemaphore.await();
		
		
		//create parent node
//		zk.create("/testRoot", "testRoot".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		//create sub node
		//CreateMode设置为临时节点会在当前程序结束前一直存在，然后删除
//		Thread.sleep(5000);
//		String ret = zk.create("/testRoot/children","children data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//		System.out.println(ret);
		
//		zk.create("/app/c1", "c1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

//		modify node data
//		zk.setData("/testRoot","modify data root".getBytes(), -1);

//		is exists node
//		System.out.println("==========" + zk.exists("/testRoot",false));
		
//		get node info
//		byte[] data = zk.getData("/testRoot/children",false, null);
//		System.out.println(new String(data));
//		List<String> list = zk.getChildren("/testRoot",false);
//		System.out.println(list);
		
//		zk.delete("/testRoot/c1", -1,
//				new AsyncCallback.VoidCallback() {
//					@Override
//					public void processResult(int rc, String path, Object ctx) {
//						try {
//							Thread.sleep(5000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						System.out.println(rc);
//						System.out.println(path);
//						System.out.println(ctx);
//					}
//				},"a");
//		System.out.println("over");
		
		zk.close();
	};
}
