package Netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by wincher on 30/08/2017.
 */
public class StrServer {
	
	private int port;
	private NioEventLoopGroup eventExecutors;
	
	public StrServer(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) {
		
		try {
			new StrServer(8889).start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void start() throws InterruptedException {
		
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			
			eventExecutors = new NioEventLoopGroup();
			
			serverBootstrap.group(eventExecutors)
					.channel(NioServerSocketChannel.class)
					.localAddress("localhost",port)
					.childHandler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel channel) throws Exception {
							channel.pipeline().addLast(new StrServerHandler());
						}
					});
			ChannelFuture channelFuture = serverBootstrap.bind().sync();
			
			System.out.println("Server -> 服务器启动成功，监听端口:" + port);
			
			channelFuture.channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			eventExecutors.shutdownGracefully().sync();
		}
	}
}
