package NetworkPrograming.Netty.heartbeat;

import NetworkPrograming.Netty.DataTransfer.Request;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashMap;

/**
 * Created by wincher on 24/09/2017.
 */
public class ServerHeartBeatHandler extends ChannelInboundHandlerAdapter {
	private static HashMap<String, String> AUTH_IP_MAP = new HashMap<String,String>();
	private static final String SUCCESS_KEY = "auth_success_key";
	
	static {
		AUTH_IP_MAP.put("127.0.0.1","1234");
	}
	
	private boolean auth(ChannelHandlerContext ctx, Object msg) {
		System.out.println(msg);
		String[] ret = ((String)msg).split(",");
		String auth = AUTH_IP_MAP.get(ret[0]);
		if (auth != null && auth.equals(ret[1])) {
			ctx.writeAndFlush(SUCCESS_KEY);
			return true;
		} else {
			ctx.writeAndFlush("auth failure!").addListener(ChannelFutureListener.CLOSE);
			return false;
		}
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof String) {
			auth(ctx, msg);
		} else if (msg instanceof Request) {
			Request info = (Request) msg;
			
			//read info
			
			ctx.writeAndFlush("info received!");
		} else {
			ctx.writeAndFlush("connect fail!").addListener(ChannelFutureListener.CLOSE);
		}
	}
}
