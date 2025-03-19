package NetworkPrograming.Netty.Serializable;

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
	
	private final AtomicInteger index = new AtomicInteger(0);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ServerHandler.channelRead() called!");

		System.out.println(msg);
		Request req = (Request) msg;
		System.out.println("Server -> " + req.getId() + ", " + req.getMessage());
		
		byte[] attachment = GzipUtil.ungzip(req.getAttachment());

		// 使用相对路径，在当前工作目录下创建 server_received 目录
		String relativePath = "AboutIO/src/main/resources/server_received";
		File directory = new File(relativePath);
		if (!directory.exists()) {
			boolean mkdir = directory.mkdir();
			if (!mkdir) System.err.println("Failed to create directory!");
			return;
		}
		int no = index.getAndIncrement();
		String path = relativePath + File.separatorChar + no + ".jpg";
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(attachment);
		fos.close();
		
		Request res = new Request(String.valueOf(no),"message from server");
		ctx.writeAndFlush(res);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.err.println("Exception caught in ServerHandler: " + cause.getMessage());
		ctx.close();
	}
}
