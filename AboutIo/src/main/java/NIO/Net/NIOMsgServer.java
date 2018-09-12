package NIO.Net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wincher on 01/09/2017.
 */
public class NIOMsgServer {
	private int port;
	private String host;
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	private boolean isRunning = true;
	
	public NIOMsgServer(String host, int port) {
		this.host = host;
		this.port = port;
		init();
	}
	
	private void init() {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			//绑定监听端口
			serverSocketChannel.socket().bind(new InetSocketAddress(port));
			//设置成为阻塞
			serverSocketChannel.configureBlocking(false);
			
			//和selector绑定，并注册事件监听
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("Server -> running at port " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		while (isRunning) {
			try {
				selector.select(1000);
				//请求在nio里对应就是一个key，内核通知给selecotr
				//selector可以拿到各种通知
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				SelectionKey selectionKey = null;
				while (iterator.hasNext()) {
					selectionKey = iterator.next();
					iterator.remove();
					try {
						handleKey(selectionKey);
					} catch (IOException e) {
						selectionKey.cancel();
						if (selectionKey.channel() != null) {
							selectionKey = null;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleKey(SelectionKey selectionKey) throws IOException {
		// 判断这个事件是否还有效
		if (selectionKey.isValid()) {
			//处理连接事件
			if (selectionKey.isAcceptable()) {
				//获取客户端连接通道
				ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
				//相应客户端建立连接
				serverSocketChannel.accept();
				serverSocketChannel.configureBlocking(false);
				//连接建立完成
				serverSocketChannel.register(selector, SelectionKey.OP_READ);
			}
			
			//处理数据读取事件
			if (selectionKey.isReadable()) {
				SocketChannel channel = (SocketChannel)selectionKey.channel();
				
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readLength = channel.read(readBuffer);
				
				if (readLength > 0) {
					//为了保证tcp包的完整调用此方法
					readBuffer.flip();
					//返回剩余可用长度，实际的数据长度
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					
					String request = new String(bytes,"UTF-8");
					System.out.println("NIO -> request body: " + request);
					//------------⬆️读数据 ========== ⬇️写数据----------------
					String response = request.equals("GET CURRENT TIME") ? new Date().toString() : "Bad request!";
					
					byte[] responseBytes = response.getBytes();
					ByteBuffer sendBuffer = ByteBuffer.allocate(responseBytes.length);
					sendBuffer.put(responseBytes);
					sendBuffer.flip();
					
				}
				
			}
		}
	}
}
