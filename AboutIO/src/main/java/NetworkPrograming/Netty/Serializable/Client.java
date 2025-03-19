package NetworkPrograming.Netty.Serializable;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.*;

/**
 * @author wincher
 * @date   20/09/2017.
 */
public class Client {
	private final int PORT;
	private final String HOST;
	private EventLoopGroup eventExecutors;
	private Bootstrap bootstrap;
	
	public Client(String host, int port) {
		PORT = port;
		HOST = host;
	}
	
	public void connect() {
		eventExecutors = new NioEventLoopGroup();
		bootstrap = new Bootstrap()
				.channel(NioSocketChannel.class).group(eventExecutors)
				.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
					socketChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecoder());
					socketChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
					socketChannel.pipeline().addLast(new ClientHandler());
				}
			});

		try {
			ChannelFuture cf = bootstrap.connect(HOST, PORT).sync();
			System.out.println("Client connected to " + HOST + ":" + PORT); // 添加日志输出

			for (int i = 0; i < 3; i++) {
				Request req = new Request(String.valueOf(i+1), "message" + (i + 1));

				// 使用 Classpath 读取资源文件
				InputStream is = getClass().getClassLoader().getResourceAsStream("pic0" + i + ".jpg");
				if (is == null) {
					System.err.println("can not find: pic0" + i + ".jpg");
					continue; // 或者抛出异常
				}
				byte[] data = new byte[is.available()];
				is.read(data);
				is.close();

				if (data.length == 0) {
					System.err.println("file pic0" + (i + 1) + ".jpg is null");
					continue; // or throw
				}

				byte[] compressedData = GzipUtil.gzip(data);
				req.setAttachment(compressedData);
				cf.channel().writeAndFlush(req).sync(); // 确保数据被成功发送
				System.out.println("Client sent request: " + req.getId()); // 添加日志输出
			}
			cf.channel().closeFuture().sync();
		} catch (Exception e) {
			System.err.println("Exception in Client.connect(): " + e.getMessage());
		} finally {
			eventExecutors.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		Client c = new Client("127.0.0.1", 8765);
		c.connect();
	}
	
}
