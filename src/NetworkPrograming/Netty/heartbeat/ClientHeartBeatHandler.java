package NetworkPrograming.Netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by wincher on 24/09/2017.
 */
public class ClientHeartBeatHandler extends ChannelInboundHandlerAdapter {
	
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> heartBeat;
	private InetAddress addr;
	private static final String SUCCESS_KEY = "auth_success_key";
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		addr = InetAddress.getLocalHost();
		ctx.writeAndFlush(addr.getHostAddress());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			if (msg instanceof String) {
				String ret = (String) msg;
				if (SUCCESS_KEY.equals(ret)) {
					this.heartBeat = this.scheduler.scheduleWithFixedDelay(new HeartBeatTask(ctx), 0, 2, TimeUnit.SECONDS);
					System.out.println(msg);
				} else {
					System.out.println(msg);
				}
			}
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}
}
