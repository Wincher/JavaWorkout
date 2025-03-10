package NetworkPrograming.Netty.Serializable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wincher
 * @date   20/09/2017.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
	
	private AtomicInteger index = new AtomicInteger(0);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		System.out.println(msg);
		Request req = (Request) msg;
		System.out.println("Server -> " + req.getId() + ", " + req.getMessage());
		
		byte[] attachment = GzipUtil.ungzip(req.getAttachment());

		String path = System.getProperty("user.dir") + File.separatorChar + "sources" +
				File.separatorChar + index.getAndIncrement() + ".jpg";

		FileOutputStream fos = new FileOutputStream(path);
		fos.write(attachment);
		fos.close();
		
		Request res = new Request("1","message1");
		ctx.writeAndFlush(res);
	}
	
}
