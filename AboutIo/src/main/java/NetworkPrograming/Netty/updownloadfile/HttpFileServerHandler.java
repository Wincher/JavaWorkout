package NetworkPrograming.Netty.updownloadfile;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * Created by wincher on 24/09/2017.
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	
	private final String url;
	public HttpFileServerHandler(String url) {
		this.url = url;
	}
	
	@Override
	protected void messageReceived(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
	
	}
}
