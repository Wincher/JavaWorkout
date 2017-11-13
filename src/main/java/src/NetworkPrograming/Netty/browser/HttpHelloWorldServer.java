package NetworkPrograming.Netty.browser;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * Created by wincher on 24/09/2017.
 */
public final class HttpHelloWorldServer {
	static final boolean SSL = System.getProperty("ssl") != null;
	static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "8443" : "8080"));
	
	public static void main(String[] args) throws CertificateException, SSLException {
		// Configure SSL
		final SslContext sslContext;
		if (SSL) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslContext = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
		} else {
			sslContext = null;
		}
		
		//Configure the Server
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new HttpHelloWorldServerInitializer(sslContext));
			
			Channel ch = b.bind(PORT).sync().channel();
			System.err.println("Open your web browser and navigate to " +
					(SSL? "https" : "http") + "://127.0.0.1:" + PORT + '/');
			ch.closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
