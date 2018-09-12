package NetworkPrograming.Netty.DataTransfer;

import NetworkPrograming.Netty.Serializable.MarshallingCodeFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by wincher on 22/09/2017.
 */
public class Client {
	
	private static class SingletonHolder {
		static final Client instance = new Client();
	}
	
	public static Client getInstance() {
		return SingletonHolder.instance;
	}
	
	private EventLoopGroup group;
	private Bootstrap b;
	private ChannelFuture cf;
	
	private Client() {
		group = new NioEventLoopGroup();
		b = new Bootstrap();
		b.group(group)
				.channel(NioSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO))
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecoder());
						socketChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
						//超时handler，当服务器与客户端在指定事件以上没有任何进行通信，则会关闭响应的通道，主要为减小服务器端资源占用
						socketChannel.pipeline().addLast(new ReadTimeoutHandler(5));
						socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter());
					}
				});
	}
	
	public void connect() {
		try {
			this.cf = b.connect("127.0.0.1", 8765).sync();
			System.out.println("Client start: ready to transfer data...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public ChannelFuture getChannelFuture() {
		if (this.cf == null) {
			this.connect();
		}
		if (!this.cf.channel().isActive()) {
			this.connect();
		}
		return this.cf;
	}
	
	public static void main(String[] args) throws InterruptedException {
		final Client c = Client.getInstance();
		
		ChannelFuture channelFuture = c.getChannelFuture();
		
		for (int i = 0; i < 3; i++) {
			Request request = new Request("" + i, "pro" + i, "data info" + i);
			channelFuture.channel().writeAndFlush(request);
			TimeUnit.SECONDS.sleep(4);
		}
		
		channelFuture.channel().closeFuture().sync();
		
		new Thread(() -> {
			try {
				System.out.println("enter child thread:");
				ChannelFuture cf = c.getChannelFuture();
				System.out.println(cf.channel().isActive());
				System.out.println(cf.channel().isOpen());
				
				//
				Request request = new Request("" + 4, "pro" + 4, "data info" + 4);
				cf.channel().writeAndFlush(request);
				cf.channel().closeFuture().sync();
				System.out.println("child thread stop;");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		
		System.out.println("断开连接，主线程结束");
	}
}
