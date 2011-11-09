package xcon.pilot.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	private static final int PORT = 8001;

	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + PORT);
			System.exit(1);
		}

		Socket clientSocket = null;
		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}

		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));

		System.out.println("Echo server started");

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			System.out.println("echoing: " + inputLine);
			out.println(inputLine);
		}
		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
	}
}