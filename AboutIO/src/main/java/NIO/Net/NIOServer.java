package NIO.Net;

/**
 * @author wincher
 * @date   01/09/2017.
 */
public class NIOServer {
	public static void main(String[] args) {
		int port = 8899;
		String host = "127.0.0.1";
		//因为是nio，所以不需要线程干预
		new NIOMsgServer(host, port).start();
	}
}
