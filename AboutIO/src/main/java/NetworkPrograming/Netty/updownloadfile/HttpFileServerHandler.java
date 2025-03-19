package NetworkPrograming.Netty.updownloadfile;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 *
 * @author wincher
 * @date 24/09/2017
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	
	private final String url;
	public HttpFileServerHandler(String url) {
		this.url = url;
	}

	private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");
	private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		System.out.println(Thread.currentThread().getName() + ": " + msg.uri() + " " + msg.method() + " " + url + " " + msg.headers());
		//首先对HTTP请求的解码结果进行判断
		if (!msg.decoderResult().isSuccess()) {
			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}
		//对HTTP请求方法进行判断，只允许GET方法
//		if (msg.method() != HttpMethod.GET) {
//			sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
//			return;
//		}
		//对请求的URI进行包装
		final String uri = msg.uri();
		final String path = sanitizeUri(uri);
		//如果URI校验失败，则返回错误
		if (path == null) {
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}
		//使用新路径构建File对象
		File file = new File(path);
		//如果文件不存在或者是隐藏文件，则请求失败
		if (file.isHidden() || !file.exists()) {
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			return;
		}
		//如果是目录，则发送目录的连接给客户端
		if (file.isDirectory()) {
			if (uri.endsWith("/")) {
				sendListing(ctx, file);
			} else {
				sendRedirect(ctx, uri + '/');
			}
			return;
		}
		//如果文件是文本类型，则判断是否允许读取
		if (!file.isFile()) {
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}

		// RandomAccessFile以只读的方式打开文件
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "r");// 以只读的方式打开文件
		} catch (FileNotFoundException fnfe) {
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			return;
		}

		long fileLength = randomAccessFile.length();
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		HttpUtil.setContentLength(response, fileLength);
		setContentTypeHeader(response, file);
		//判断是否是Keep-Alive，是则添加对应的header
		if (HttpUtil.isKeepAlive(msg)) {
			response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		}
		//发送响应消息
		ctx.write(response);
		//通过ChunkedFile对象直接将文件写入到发送缓冲区中
		ChannelFuture sendFileFuture = null;
		sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 8192), ctx.newProgressivePromise());
		sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
			@Override
			public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
				if (total < 0) { // total unknown
					System.err.println(future.channel() + " Transfer progress: " + progress);
				} else {
					System.err.println(future.channel() + " Transfer progress: " + progress + " / " + total);
				}
			}

			@Override
			public void operationComplete(ChannelProgressiveFuture future) throws Exception {
				System.out.println(future.channel() + " Transfer complete.");
			}
		});

		//如果使用chunked编码，最后需要发送一个编码结束的空消息体，将所有消息发送完成
		ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		//如果不是Keep-Alive，服务器完成所有消息的发送后，主动关闭连接
		if (!HttpUtil.isKeepAlive(msg)) {
			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.err.println(cause.getMessage());
		if (ctx.channel().isActive()) {
			sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private static final String STATUS_URI = "/status";

	private String sanitizeUri(String uri) throws UnsupportedEncodingException {
		// 1.使用java.net.URLDecoder的decode方法对uri进行解码
		uri = URLDecoder.decode(uri, "UTF-8");
		if (uri.isEmpty() ) return null;
		if (uri.startsWith("/")) uri.replaceFirst("/", "");
		// 4.使用replace方法将uri中的..替换成.
		uri = uri.replace('/', File.separatorChar);
		// 5.再次进行判断，如果uri中包含..或者.开头，则直接返回null
		if (uri.contains("..") || uri.startsWith(".") || INSECURE_URI.matcher(uri).matches()) {
			return null;
		}

		// 6.使用当前目录 + uri构造绝对路径进行返回
		return System.getProperty("user.dir") + File.separator + uri;
	}

	private static void sendListing(ChannelHandlerContext ctx, File dir) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
		StringBuilder buf = new StringBuilder();
		String dirPath = dir.getPath();
		buf.append("<!DOCTYPE html>\r\n");
		buf.append("<html><head><title>");
		buf.append("Directory listing for: ");
		buf.append(dirPath);
		buf.append("</title></head><body>\r\n");
		buf.append("<h3>Directory listing for: ");
		buf.append(dirPath);
		buf.append("</h3>\r\n");
		buf.append("<ul>");
		buf.append("<li><a href=\"../\">..</a></li>\r\n");
		for (File f : dir.listFiles()) {
			if (f.isHidden() || !f.canRead()) {
				continue;
			}
			String name = f.getName();
			if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
				continue;
			}
			buf.append("<li><a href=\"");
			buf.append(name);
			buf.append("\">");
			buf.append(name);
			buf.append("</a></li>\r\n");
		}
		buf.append("</ul></body></html>\r\n");
		ByteBuf buffer = Unpooled.copiedBuffer(buf, CharsetUtil.UTF_8);
		response.content().writeBytes(buffer);
		buffer.release();
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
		response.headers().set(HttpHeaderNames.LOCATION, newUri);
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private static void setContentTypeHeader(HttpResponse response, File file) {
		Path path = Paths.get(file.getPath());
		try {
			String contentType = Files.probeContentType(path);
			if (contentType == null) {
				// 无法确定 Content-Type，使用默认值或者抛出异常
				contentType = "application/octet-stream"; // 默认值
			}
			System.out.println("Content-Type:" + contentType);
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
		} catch (IOException e) {
			// 处理 IOException，例如记录日志或者使用默认值
			System.err.println("Error probing Content-Type: " + e.getMessage());
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/octet-stream"); // 默认值
		}
	}
}
