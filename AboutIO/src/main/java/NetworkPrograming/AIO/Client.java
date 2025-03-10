package NetworkPrograming.AIO;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

/**
 * @author wincher
 * @date   15/09/2017.
 */
public class Client implements Runnable{
	private AsynchronousSocketChannel asc;
	
	public Client() throws Exception {
		asc = AsynchronousSocketChannel.open();
	}
	
	public void connect() {
		asc.connect(new InetSocketAddress("127.0.0.1", 8765));
	}

	public void write(String request) {
		try {
			asc.write(ByteBuffer.wrap(request.getBytes())).get();
			read();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	private void read() {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		try {
			asc.read(buf).get();
			buf.flip();
			byte[] respByte = new byte[buf.remaining()];
			buf.get(respByte);
			System.out.println(new String(respByte,"UTF-8").trim());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//由于aio为异步，只是为了让jvm继续运行
	@Override
	public void run() {
		while (true) {
		
		}
	}
	
	public static void main(String[] args) throws Exception {
		Client c1 = new Client();
		c1.connect();
		
		Client c2 = new Client();
		c2.connect();
		
		Client c3 = new Client();
		c3.connect();
		
		new Thread(c1,"c1").start();
		new Thread(c2,"c2").start();
		new Thread(c3,"c3").start();
		
		Thread.sleep(1000);
		
		c1.write("c1 aaa");
		c2.write("c2 ffff");
		c3.write("c3 xxxxx");
		
	}
}
