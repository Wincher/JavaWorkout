package NetworkPrograming.Netty.DataTransfer;

import NetworkPrograming.Netty.Serializable.MarshallingCodeFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @author wincher
 * @date   22/09/2017.
 */
public class Server {
	public static void main(String[] args) throws InterruptedException {
		runServer();
	}
	
	private static void runServer() throws InterruptedException {
		
		EventLoopGroup pGroup = new NioEventLoopGroup();
		EventLoopGroup cGroup = new NioEventLoopGroup();
		
		ServerBootstrap b = new ServerBootstrap();
		b.group(pGroup, cGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
						socketChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecoder());
						socketChannel.pipeline().addLast(new ReadTimeoutHandler(5));
						socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter());
					}
				});
		
		ChannelFuture cf = b.bind(8765).sync();
		
		cf.channel().closeFuture().sync();
		pGroup.shutdownGracefully();
		cGroup.shutdownGracefully();
	}
}
