package NetworkPrograming.Netty.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * Created by wincher on 16/09/2017.
 */
public class Client {
	
	public static void main(String[] args) throws InterruptedException {
		
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap()
				.channel(NioSocketChannel.class)
				.group(group)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						//设置特殊分隔符
						ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
						socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
						//设置字符串形式的解码
						//socketChannel.pipeline().addLast(new StringDecoder());
						socketChannel.pipeline().addLast(new ClientHandler());
					}
				});
		
		ChannelFuture cf1 = b.connect("127.0.0.1", 8765).sync();
		//可以绑定多个端口
		//ChannelFuture cf2 = b.connect("127.0.0.1", 8764).sync();
		
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("Hello netty!".getBytes()));
		Thread.sleep(1000);
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("Hello netty!".getBytes()));
		Thread.sleep(1000);
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("Hello netty!".getBytes()));
		
		
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("aaa$_".getBytes()));
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("bbbb$_".getBytes()));
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("ccccc$_".getBytes()));
		
		cf1.channel().closeFuture().sync();
		
		group.shutdownGracefully();
	}
	
	
}
