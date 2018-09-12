package NetworkPrograming.NIO;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Created by wincher on 15/09/2017.
 */
public class Server implements Runnable{
	
	//多路复用器（管理所有的通道）
	private Selector selector;
	// 建立缓冲区
	private ByteBuffer readBuf = ByteBuffer.allocate(1024);
	
	private ByteBuffer writeBuf = ByteBuffer.allocate(1024);
	
	public Server(int port) {
		try {
			//打开多路复用器
			selector = Selector.open();
			//打开服务器通道
			ServerSocketChannel ssc = ServerSocketChannel.open();
			//设置服务器通道为非阻塞模式
			ssc.configureBlocking(false);
			//绑定地址
			ssc.bind(new InetSocketAddress(port));
			//服务器通道注册到多路复用器上，并且监听阻塞事件
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("Server start, port : " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				//必须要让多路复用器开始监听
				this.selector.select();
				//返回多路复用器已经选择的结果集
				Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
				//进行遍历
				while (keys.hasNext()) {
					//获取一个选择的元素
					SelectionKey key = keys.next();
					//直接从容器中移除就可以了
					keys.remove();
					//如果是有效的
					if (key.isValid()) {
						//如果为阻塞状态
						if (key.isAcceptable()) {
							this.accept(key);
						}
						//如果为可读状态
						if (key.isReadable()) {
							this.read(key);
						}
						//如果为可写状态
						if (key.isWritable()) {
							this.write(key);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	private void write(SelectionKey key) {
		ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
		try {
			ssc.register(this.selector, SelectionKey.OP_WRITE);
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		}
	}
	
	private void read(SelectionKey key) {
		try {
			//清空传冲去旧的数据
			this.readBuf.clear();
			//获取之前注册的socket通道对象
			SocketChannel sc = (SocketChannel) key.channel();
			//读取数据
			int count = sc.read(this.readBuf);
			//如果没有数据
			if (count == -1) {
				key.channel().close();
				key.cancel();
				return;
			}
			//有数据则进行读取，读取之前需要进行复位方法(position和limit进行复位)
			this.readBuf.flip();
			//根据缓冲区的数据长度创建相应大小的byte数组，接收缓冲区的数据
			byte[] bytes = new byte[this.readBuf.remaining()];
			//接收缓冲区的数据
			this.readBuf.get(bytes);
			//print result
			String body = new String(bytes).trim();
			System.out.println("Server: " + body);
			//可以写回客户端数据
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void accept(SelectionKey key) {
		try {
			//获取服务器通道
			ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
			//执行阻塞方法
			SocketChannel sc = ssc.accept();
			//设置阻塞模式
			sc.configureBlocking(false);
			//注册到多路复用器上，并设置读取标示
			sc.register(this.selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Thread(new Server(8765)).start();
	}
}
