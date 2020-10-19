package NetworkPrograming.Netty.browser;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;


/**
 * @author wincher
 * @date   24/09/2017.
 */
public class HttpHelloWorldServerInitializer extends ChannelInitializer<SocketChannel> {
	
	private final SslContext sslContext;
	
	public HttpHelloWorldServerInitializer(SslContext sslContext) {
		this.sslContext = sslContext;
	}
	
	
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline  p = socketChannel.pipeline();
		if (sslContext != null) {
			p.addLast(sslContext.newHandler(socketChannel.alloc()));
		}
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpHelloWorldServerHandler());
		
	}
}
