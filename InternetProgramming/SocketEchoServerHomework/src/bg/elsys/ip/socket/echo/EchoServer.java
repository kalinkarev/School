package bg.elsys.ip.socket.echo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
	
	public static final int PORT = 3004;
	
	public static void main(String[] args) throws IOException {
		try(ServerSocket serverSocket = new ServerSocket(PORT); 
				Socket clientSocket = serverSocket.accept(); // listen for the client
				PrintStream out = 
						new PrintStream(clientSocket.getOutputStream()); // open the output
				Scanner in = new Scanner(clientSocket.getInputStream());) { // open the input
				
			String inputLine; // string reference
			
			while ((inputLine = in.nextLine()) != null) {
				out.println("Received: " + inputLine);
			}	
		}
	}
}