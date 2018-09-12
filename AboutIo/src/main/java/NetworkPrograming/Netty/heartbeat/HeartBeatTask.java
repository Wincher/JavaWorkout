package NetworkPrograming.Netty.heartbeat;

import NetworkPrograming.Netty.DataTransfer.Request;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by wincher on 24/09/2017.
 */
public class HeartBeatTask implements Runnable {
	
	private final ChannelHandlerContext ctx;
	
	public HeartBeatTask(ChannelHandlerContext ctx){
		this.ctx = ctx;
	}
	
	@Override
	public void run() {
		Request info = new Request();
		
		ctx.writeAndFlush(info);
	}
	
	
}
