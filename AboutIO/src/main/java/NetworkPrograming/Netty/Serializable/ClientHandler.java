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
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("Received message: " + msg.getClass().getName()); // 添加日志输出
		try {
			Request req = (Request) msg;
			System.out.println("Client -> " + req.getId() + ", " + req.getMessage());
		} finally {
			ReferenceCountUtil.release(msg);
		}
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.err.println("Exception caught in ClientHandler: " + cause.getMessage()); // 添加异常处理
		ctx.close();
	}
}
