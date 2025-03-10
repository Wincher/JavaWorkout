package NetworkPrograming.Netty.browser;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContextBuilder;


/**
 * @author wincher
 * @date   24/09/2017.
 */
public class HttpHelloWorldServerInitializer extends ChannelInitializer<SocketChannel> {
	
	private final SslContextBuilder sslContextBuilder;
	
	public HttpHelloWorldServerInitializer(SslContextBuilder sslContextBuilder) {
		this.sslContextBuilder = sslContextBuilder;
	}
	
	
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline  p = socketChannel.pipeline();
		if (sslContextBuilder != null) {
			p.addLast(sslContextBuilder.build().newHandler(socketChannel.alloc()));
		}
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpHelloWorldServerHandler());
		
	}
}
