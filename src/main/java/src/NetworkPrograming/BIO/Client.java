package NetworkPrograming.BIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by wincher on 14/09/2017.
 */
public class Client {
	
	final static String ADDRESS = "127.0.0.1";
	
	final static int PORT = 8765;
	
	public static void main(String[] args) {
		
		try (
				Socket socket = new Socket(ADDRESS, PORT);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		) {
			out.println("Receive client request ...");
			String response = in.readLine();
			System.out.println("Client: " + response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}