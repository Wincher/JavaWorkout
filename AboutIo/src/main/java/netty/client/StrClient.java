package netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by wincher on 30/08/2017.
 */
public class StrClient {
	
	private String host;
	private int port;
	private NioEventLoopGroup eventExecutors;
	
	public StrClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public static void main(String[] args) {
		try {
			new StrClient("localhost",8889).start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void start() throws InterruptedException {
		try {
			//客户端的引导类，启动网络客户端
			Bootstrap bootstrap = new Bootstrap();
			//构造bootstrap对象
			
			// 可以理解成是一个线程池，用这个线程池来处理连接和接收数据
			eventExecutors = new NioEventLoopGroup();
			
			bootstrap.group(eventExecutors) //多线程处理， 注册一下这个组
					.channel(NioSocketChannel.class) //制定通道类型NioSocketChannel
					.remoteAddress(new InetSocketAddress(host, port))  //注册远程服务器地址
					.handler(new ChannelInitializer<SocketChannel>() {
						//------------- ⬆️模版代码 ---------------------
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							// 业务逻辑
							socketChannel.pipeline().addLast(new StrClientHandler());
						}
						
						//-------------- ⬇️模版代码-----------------------
					});
			
			//开始连接服务器
			ChannelFuture channelFuture = bootstrap.connect().sync(); //等到连接成功，否则一直阻塞线程
			channelFuture.channel().closeFuture().sync(); //接收数据之后阻塞
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			eventExecutors.shutdownGracefully().sync();
		}
		
	}
}
