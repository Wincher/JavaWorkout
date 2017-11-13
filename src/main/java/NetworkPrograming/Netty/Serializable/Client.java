package NetworkPrograming.Netty.Serializable;

import com.wincher.utils.GzipUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by wincher on 20/09/2017.
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
	
	public void connect() throws InterruptedException, IOException {
		eventExecutors = new NioEventLoopGroup();
		bootstrap = new Bootstrap()
				.channel(NioSocketChannel.class).group(eventExecutors)
				.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecoder());
					socketChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
					socketChannel.pipeline().addLast(new ClientHandler());
				}
			});
		ChannelFuture cf = bootstrap.connect(HOST,PORT).sync();
		
		for (int i = 0; i < 3; i++) {
			Request req = new Request((i+1)+"", "message" + (i+1));
			
			String path = System.getProperty("user.dir") + File.separatorChar +
					"sources" + File.separatorChar + "Aerial0" + (i+1) + ".jpg";
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[fis.available()];
			fis.read(data);
			fis.close();
			req.setAttachment(GzipUtil.gzip(data));
			cf.channel().writeAndFlush(req);
		}
		cf.channel().closeFuture().sync();
		eventExecutors.shutdownGracefully();
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		Client c = new Client("127.0.0.1", 8765);
		c.connect();
	}
	
}
