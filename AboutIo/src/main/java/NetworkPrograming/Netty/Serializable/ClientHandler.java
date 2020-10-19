package NetworkPrograming.Netty.Serializable;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author wincher
 * @date   20/09/2017.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			Request req = (Request) msg;
			System.out.println("Client -> " + req.getId() + ", " + req.getMessage());
		} finally {
			ReferenceCountUtil.release(msg);
		}
		
	}
}
