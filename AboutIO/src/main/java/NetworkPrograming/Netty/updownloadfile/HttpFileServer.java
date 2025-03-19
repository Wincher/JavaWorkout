package NetworkPrograming.Netty.updownloadfile;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * @author wincher
 * @date   24/09/2017.
 */
public class HttpFileServer {
	private static final String DEFAULT_URL = "/sources/";
	
	public void run(final int port, final String url) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							ChannelPipeline p = socketChannel.pipeline();
							//使用 HttpServerCodec 替换 HttpRequestDecoder 和 HttpRequestEncoder
							p.addLast("http-codec", new HttpServerCodec());
							//加入ObjectAggregator解码器，作用是他会把多个消息转换为单一的FullHttpRequest或者FullHttpResponse
							p.addLast("http-aggregator", new HttpObjectAggregator(65536));
							//加入chunked 主要作用是支持一步发送的码流（大文件传输），防止java内存溢出
							p.addLast("http-chunked", new ChunkedWriteHandler());
							//加入自定义处理文件服务器的业务逻辑handler
							p.addLast("fileServerHandler", new HttpFileServerHandler(url));
						}
					});
			ChannelFuture cf = b.bind(port).sync();
			System.out.println("Http file server start at: ");
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		int port = 8765;
		new HttpFileServer().run(port, DEFAULT_URL);
	}
}
