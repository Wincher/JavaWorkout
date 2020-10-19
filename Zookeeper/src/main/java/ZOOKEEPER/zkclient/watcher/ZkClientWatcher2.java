package ZOOKEEPER.zkclient.watcher;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import java.util.List;

/**
 * @author wincher
 * @date   17/10/2017.
 */
public class ZkClientWatcher2 {
	static final String CONNECT_ADDR = "192.168.0.111:2181,192.168.0.112:2181,192.168.0.113:2181";
	static final int SESSION_OUTTIME = 5000;
	
	public static void main(String[] args) throws InterruptedException {
		ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), 10000);
		
		zkc.createPersistent("/super", "1234");
		//对父节点添加监听子节点变化
		zkc.subscribeDataChanges("/super", new IZkDataListener() {
			@Override
			public void handleDataChange(String path, Object data) throws Exception {
				System.out.println("changed node: " + path + ", content changed to:" + data);
			}
			
			@Override
			public void handleDataDeleted(String path) throws Exception {
				System.out.println("deleted node: " + path);
			}
		});
		
		Thread.sleep(3000);
		zkc.writeData("/super", "456", -1);
		Thread.sleep(1000);
		
		zkc.delete("/super");
		
		Thread.sleep(Integer.MAX_VALUE);
	}
}
