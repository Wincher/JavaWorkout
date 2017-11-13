package ZOOKEEPER.zookeeper.cluster;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wincher on 17/10/2017.
 */
public class Test {
	
	static final String CONNECT_ADDR = "192.168.0.111:2181,192.168.0.112:2181,192.168.0.113:2181";
	
	static final int SESSION_TIMEOUT = 2000;
	static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		
		ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_TIMEOUT, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				Event.KeeperState keeperState = event.getState();
				Event.EventType eventType = event.getType();
				if (Event.KeeperState.SyncConnected == keeperState) {
					if (Event.EventType.None == eventType) {
						System.out.println("zk connected-----");
						connectedSemaphore.countDown();
					}
				}
			}
		});
		
		connectedSemaphore.await();
		
		zk.create("/super/c1", "c1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/super/c2", "c2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/super/c3", "c3".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/super/c4", "c4".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		zk.setData("/super/c1", "modify c1".getBytes(), -1);
		zk.setData("/super/c2", "modify c2".getBytes(), -1);
		
		byte[] data = zk.getData("/super/c2", false, null);
		System.out.println(new String(data));
		
		System.out.println(zk.exists("/super/c3", false));
		
		zk.delete("/super/c3", -1);
		
		zk.close();
	}
}
