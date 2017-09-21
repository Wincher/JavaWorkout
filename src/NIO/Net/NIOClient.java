package NIO.Net;

/**
 * Created by wincher on 01/09/2017.
 */
public class NIOClient {
	public static void main(String[] args) {
		int port = 8899;
		String host = "127.0.0.1";
		System.out.println("NIOClient -> connect to server at port:" + port);
		new MsgClient(host, port).start();
	}
}
