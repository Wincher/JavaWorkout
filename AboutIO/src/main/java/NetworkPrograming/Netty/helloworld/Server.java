package NetworkPrograming.Netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author wincher
 * @date   16/09/2017.
 * 对于ChannelOption.SO_BACKLOG的解释：
 * 服务器断TCP内核模块维护有两个队列，我们称之为A，B吧，
 * 客户端向服务器端connect的时候，会发送带有SYN标志的包（第一次握手），
 * 服务器收到客户端法老的SYN时，向客户端发送SYN_ACK确认(第二次握手)，
 * 此时TCP内核模块把客户端连接加入到A队列中，然后服务器收到客户端发来的ACK（第三次握手），
 * TCP内核陌路爱会把客户端连接送A队列移到B'队列，连接完成，应用程序的accept会返回。
 * 也就是说accept从B队列中取出完成三次握手的连接。
 * A队列和B队列的长度之和就是backlog。当A，B队列的长度之和大于backlog时，新连接将会被TCP内核拒绝，
 * 所以，如果backlog过小，可能会出现accept速度跟不上，A，B队列满了，导致新的客户端无法连接，
 * 要注意的是:backlog对程序支持的连接数并无影响，backlog影响的只是还没有被accept取出的连接
 */
public class Server {
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup pGroup = new NioEventLoopGroup();        //一个是用于处理服务器断接收客户端连接的
		EventLoopGroup cGroup = new NioEventLoopGroup();        //一个是进行网络通信的(网络读写的)
		ServerBootstrap b = new ServerBootstrap();              //创建辅助工具类
		b.group(pGroup,cGroup)                                  //绑定两个线程组
			.channel(NioServerSocketChannel.class)              //设置nio模式
			.option(ChannelOption.SO_BACKLOG, 1024)       //设置tcp缓冲区
			.option(ChannelOption.SO_SNDBUF, 32*1024)     //设置发送缓冲大小
			.option(ChannelOption.SO_RCVBUF, 32*1024)     //设置接收缓冲大小
			.option(ChannelOption.SO_KEEPALIVE, true)     //保持连接
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					//设置特殊分隔符
					ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
					socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
					//socketChannel.pipeline().addLast()
					//设置字符串形式的解码
					//socketChannel.pipeline().addLast(new StringDecoder());
					//为通道添加handler加在最后
					socketChannel.pipeline().addLast(new ServerHandler()); //在这里配置具体数据接收方法的处理
				}
			});
		
		ChannelFuture cf1 = b.bind(8765).sync();                    //bind
		//可以绑定多个端口
		//ChannelFuture cf2 = b.bind(8764).sync();                    //bind
		
		//Thread.sleep(Integer.MAX_VALUE);
		//只是为了用netty的方法做阻塞，正常工作不建议这样使用
		cf1.channel().closeFuture().sync();                                 //wait to close
		
		pGroup.shutdownGracefully();
		cGroup.shutdownGracefully();
		
		//ChannelFuture cf2 = b.bind(8765).sync();                    //bind
		
		//cf2.channel().closeFuture().sync();                                 //wait to close
		
	}
}
