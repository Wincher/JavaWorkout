package NetworkPrograming.BIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wincher
 * @date   14/09/2017.
 */
public class Server {
	final static int PORT = 8765;
	
	public static void main(String[] args) {
		
		try (
				ServerSocket server = new ServerSocket(PORT);
				) {
			System.out.println("server start...");
			while (true) {
				//阻塞
				Socket socket = server.accept();
				//新建线程执行客户端请求
				new Thread(new ServerHandler(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
