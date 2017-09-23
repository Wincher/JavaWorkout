package Netty.cn.wincher.netty.quickStart.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Date;

/**
 * Created by wincher on 30/08/2017.
 */
public class StrServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//在服务器读取数据的时候被调用
		System.out.println("Server -> receive data, dealing...");
		ByteBuf buf = (ByteBuf) msg;
		
		byte[] bytes = new byte[buf.readableBytes()];
		//方法是将buf写入到bytes
		buf.readBytes(bytes);
		String requestBodyStr = new String(bytes,"UTF-8");
		
		System.out.println("Server -> receive content: " + requestBodyStr);
		
		System.out.println("Server -> start response...");
		String currentTime = new Date().toString();
		
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(currentTime.getBytes());
		
		ctx.write(copiedBuffer);
		
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//数据读取完成后会调用
		//发出数据
		ctx.flush();
		//给下一个channel
		//ctx.fireChannelActive();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 一场调用
		ctx.close();
		cause.printStackTrace();
	}
}
