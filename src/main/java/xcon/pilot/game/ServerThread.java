package xcon.pilot.game;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerThread extends Thread implements Runnable {

	Socket acceptedSocket;
	Player[] players;
	int playerNum;

	public ServerThread(Socket acceptedSocket, Player[] players, int playerNum) {
		super("ServerThread");
		this.acceptedSocket = acceptedSocket;
		try {
			acceptedSocket.setTcpNoDelay(true);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.players = players;
		this.playerNum = playerNum;
	}

	public void run() {
		
		try {

			Socket clientSocket = acceptedSocket;
			System.out.println("Accepted. Now creating I/O.");
			ObjectInputStream in = new ObjectInputStream(clientSocket
					.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(clientSocket
					.getOutputStream());
			System.out.println("I/O with: " + playerNum + " working.");
			out.writeInt(playerNum);
			out.flush();

			while (true) {
				if (playerNum == 0) {
					players[0].x = in.readInt();
					players[0].y = in.readInt();
					out.writeInt(players[1].x);
					out.writeInt(players[1].y);
					out.flush();
				}

				else if (playerNum == 1) {
					players[1].x = in.readInt();
					players[1].y = in.readInt();
					out.writeInt(players[0].x);
					out.writeInt(players[0].y);
					out.flush();
				}

				else if (playerNum == 2) {
					players[2].x = in.readInt();
					players[2].y = in.readInt();
					out.writeInt(players[0].x);
					out.writeInt(players[0].y);
					out.flush();
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

}