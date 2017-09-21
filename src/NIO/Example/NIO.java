package NIO.Example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wincher on 17/09/2017.
 *  代码出自：深入分析Java Web技术内幕 2.4.2 NIO的工作机制
 */
public class NIO {
	
	public void selector() throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		Selector selector = Selector.open();
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ssc.socket().bind(new InetSocketAddress(8765));
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		
		while (true) {
			Set selectedKeys = selector.selectedKeys();
			Iterator it = selectedKeys.iterator();
			while (it.hasNext()) {
				SelectionKey key = (SelectionKey) it.next();
				if ( (key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
					ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
					SocketChannel sc = ssChannel.accept();
					sc.configureBlocking(false);
					sc.register(selector, SelectionKey.OP_READ);
					it.remove();
				} else if ( (key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
					SocketChannel sc = (SocketChannel) key.channel();
					while (true) {
						buffer.clear();
						int n = sc.read(buffer);
						if (n <= 0) break;
						buffer.flip();
					}
					it.remove();
				}
			}
		}
	}
	
	public static void main(String[] args) {
	
	
	}
}
