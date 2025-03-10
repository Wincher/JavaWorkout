package NetworkPrograming.Netty.Serializable;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author wincher
 * @date   20/09/2017.
 */
public class Server {
	
	private final int PORT;
	private EventLoopGroup pGroup;
	private EventLoopGroup cGroup;
	private ServerBootstrap serverBootstrap;
	
	public Server(int port) {
		PORT = port;
	}
	
	
	public void runServer() throws InterruptedException {
		pGroup = new NioEventLoopGroup();
		cGroup = new NioEventLoopGroup();
		
		serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(pGroup, cGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG,1024)
				.option(ChannelOption.SO_RCVBUF,1024 * 32)
				.option(ChannelOption.SO_SNDBUF,1024 * 32)
				.option(ChannelOption.SO_KEEPALIVE,true)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecoder());
						socketChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
						socketChannel.pipeline().addLast(new ServerHandler());
					}
				});
		ChannelFuture cf = serverBootstrap.bind(PORT).sync();
		cf.channel().closeFuture().sync();
		pGroup.shutdownGracefully();
		cGroup.shutdownGracefully();
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Server(8765).runServer();
	}
}
