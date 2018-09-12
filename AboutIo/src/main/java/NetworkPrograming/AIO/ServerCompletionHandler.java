package NetworkPrograming.AIO;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

/**
 * Created by wincher on 15/09/2017.
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {
	
	@Override
	public void completed(AsynchronousSocketChannel result, Server attachment) {
		//当有下一个客户端介入的时候，直接调用Server的accept方法，这样返回执行下去，保证多个客户端都可以阻塞
		attachment.assc.accept(attachment, this);
		read(result);
	}
	
	@Override
	public void failed(Throwable exc, Server attachment) {
		exc.printStackTrace();
	}
	
	private void read(final AsynchronousSocketChannel asc) {
		//read data
		ByteBuffer buf = ByteBuffer.allocate(1024);
		asc.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				//进行读取之后，重置标识位
				attachment.flip();
				//获得读取的字节数
				System.out.println("Server -> " + "received from client data length:" + result);
				//获取读取的数据
				String resultData = new String(attachment.array()).trim();
				System.out.println("Server -> " + resultData);
				String response = ("Server response,received data:" + resultData);
				write(asc,response);
			}
			
			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				exc.printStackTrace();
			}
		});
	}
	
	private void write(AsynchronousSocketChannel asc, String response) {
		try {
			ByteBuffer buf = ByteBuffer.allocate(1024);
			buf.put(response.getBytes());
			buf.flip();
			asc.write(buf).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
