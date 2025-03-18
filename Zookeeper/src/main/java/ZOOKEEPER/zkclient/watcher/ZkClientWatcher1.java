package ZOOKEEPER.zkclient.watcher;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import java.util.List;

/**
 * @author wincher
 * @date   17/10/2017.
 */
@Deprecated
public class ZkClientWatcher1 {
	static final String CONNECT_ADDR = "192.168.0.111:2181,192.168.0.112:2181,192.168.0.113:2181";
	static final int SESSION_OUTTIME = 5000;
	
	public static void main(String[] args) throws InterruptedException {
		ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), 10000);
		
		//对父节点添加监听子节点变化
		zkc.subscribeChildChanges("/super", new IZkChildListener() {
			@Override
			public void handleChildChange(String s, List<String> list) throws Exception {
				System.out.println("parentPath: " + s);
				System.out.println("currentChild: " + list.toString());
			}
		});
		
		Thread.sleep(3000);
		
		zkc.createPersistent("/super");
		Thread.sleep(1000);
		
		zkc.createPersistent("/super/c1", "c1 content");
		Thread.sleep(1000);
		
		zkc.createPersistent("/super/c2", "c2 content");
		Thread.sleep(1000);
		
		//并不会监听子节点的update操作
		zkc.writeData("/super/c1", "new content");
		Thread.sleep(1000);
		
		
		zkc.delete("/super/c2");
		Thread.sleep(1000);
		
		zkc.deleteRecursive("/super");
		
		Thread.sleep(Integer.MAX_VALUE);
	}
}
