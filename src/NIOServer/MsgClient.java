package NIOServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wincher on 01/09/2017.
 */
public class MsgClient {
	private final String host;
	private final int port;
	private Selector selector;
	private SocketChannel socketChannel;
	private boolean isStart = true;
	
	public MsgClient(String host, int port) {
		this.host = host;
		this.port = port;
		init();
	}
	
	private void init() {
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		
		try {
			connectMsgServer();
			//连接
			while (isStart) {
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				
				SelectionKey selectionKey = null;
				while (iterator.hasNext()) {
					selectionKey = iterator.next();
					//注册读取数据事件
					if (selectionKey.isValid()) {
						
						SocketChannel sc = (SocketChannel) selectionKey.channel();
						
						if (selectionKey.isConnectable()) {
							socketChannel.register(selector, SelectionKey.OP_READ);
						}
						
					}
					// 读取数据事件
					if (selectionKey.isReadable()) {
						ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
						int readLength = socketChannel.read(byteBuffer);
						if (readLength > 0) {
							byteBuffer.flip();
							byte[] bytes = new byte[byteBuffer.remaining()];
							byteBuffer.get(bytes);
							
							String responseBody = new String(bytes,"UTF-8");
							
							System.out.println("NIOMsgClient -> server response: " + responseBody);
							this.isStart = false;
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void connectMsgServer() throws IOException {
		boolean isConnected = socketChannel.connect(new InetSocketAddress(host, port));
		if (isConnected) {
			//如果一下就连接上
			socketChannel.register(selector, SelectionKey.OP_READ);
			byte[] bytes = "GET CURRENT TIME".getBytes();
			ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
			byteBuffer.put(bytes);
			
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
			
			System.out.println("NIOClient -> send request...");
		} else {
			//注册等待连接事件，等待通知
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			System.out.println("NIOMsgClient -> register op_connect!");
		}
	}
}
