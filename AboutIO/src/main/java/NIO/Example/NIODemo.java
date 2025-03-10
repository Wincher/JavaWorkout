package NIO.Example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wincher
 * @date   17/09/2017.
 *  代码出自：深入分析Java Web技术内幕 2.4.2 NIO的工作机制
 */
public class NIODemo {
	
	public void selector() throws IOException {
		Selector selector = Selector.open();
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ssc.socket().bind(new InetSocketAddress(8765));
		ssc.register(selector, SelectionKey.OP_ACCEPT);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (true) {
			selector.select();
			Set selectedKeys = selector.selectedKeys();
			Iterator it = selectedKeys.iterator();
			while (it.hasNext()) {
				SelectionKey key = (SelectionKey) it.next();
				if ( (key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
					ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
					SocketChannel sc = ssChannel.accept();
					System.out.println("New client connected! -> OP_ACCEPT");
					sc.configureBlocking(false);
					sc.register(selector, SelectionKey.OP_READ);
					it.remove();
				} else if ( (key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
					SocketChannel sc = (SocketChannel) key.channel();
					System.out.println("Client data incoming -> OP_READ");
					int bytesRead;
					while (true) {
						buffer.clear();
						bytesRead = sc.read(buffer);
						if (bytesRead <= 0) {
							if (bytesRead == -1) sc.close();
							break;
						}
						buffer.flip();
						byte[] bytes = new byte[buffer.remaining()];
						buffer.get(bytes); // 读取到字节数组
						String clientData = new String(bytes, StandardCharsets.UTF_8);
						System.out.println("Read: " + clientData);
//						buffer.rewind(); // 重置 position，准备回写
//						sc.write(buffer);
						String response = "Server: " + clientData;
						ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));
						sc.write(responseBuffer);
						it.remove();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			new NIODemo().selector();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
