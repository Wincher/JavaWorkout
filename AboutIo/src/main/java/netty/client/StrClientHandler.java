package netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by wincher on 30/08/2017.
 */
public class StrClientHandler extends SimpleChannelInboundHandler<ByteBuf>{
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 客户端连接到服务器后被调用
		System.out.println("client -> 开始连接服务器发送数据...");
		byte[] bytes = "get current time".getBytes();
		
		ByteBuf buffer = Unpooled.buffer(bytes.length); //构建一个数组存数据用
		buffer.writeBytes(bytes); //在数组里写入数据
		ctx.writeAndFlush(buffer);
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
		// 在数据库接收到数据后调用
		System.out.println("client -> 开始读取服务器返回的数据...");
		
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		
		String messageBody = new String(bytes, "UTF-8");
		System.out.println("client -> 接收数据:" + messageBody);
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 发生异常时被调用
		ctx.close();
	}
}
