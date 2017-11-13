package NetworkPrograming.BIO2;

import NetworkPrograming.BIO.ServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wincher on 14/09/2017.
 */
public class Server {
	final static int PORT = 8765;
	
	public static void main(String[] args) {
		try (
				ServerSocket serverSocket = new ServerSocket(PORT);
				) {
			System.out.println("server start..");
			Socket socket = null;
			HandlerExecutorPool executorPool = new HandlerExecutorPool(50, 1000);
			while (true) {
				socket = serverSocket.accept();
				executorPool.execute(new ServerHandler(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
