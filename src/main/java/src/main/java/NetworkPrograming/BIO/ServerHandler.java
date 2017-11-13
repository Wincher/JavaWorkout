package NetworkPrograming.BIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by wincher on 14/09/2017.
 */
public class ServerHandler implements Runnable {
	
	private Socket socket;
	
	public ServerHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try (
				BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
				PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
				) {
			
			String body = null;
			while (true) {
				body = in.readLine();
				if (body == null) break;
				System.out.println("Server: " + body);
				out.println("Server response!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
