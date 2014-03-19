package xcon.pilot.game;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SlickClient extends BasicGame {

	int MAX_PLAYERS = 3;
	int playerNum = 0;
	Player[] players;
	ClientThread ct;

	int serverDelay = 15;

	public SlickClient() {
		super("Client");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		try {
			players = new Player[MAX_PLAYERS];
			players[0] = new Player(25, 25);
			players[1] = new Player(125, 125);

			ct = new ClientThread(players);
			ct.start();
			ct.setPriority(Thread.MAX_PRIORITY);

			playerNum = ct.playerNum;
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_A)) {
			players[playerNum].x -= 5;
		}

		if (input.isKeyDown(Input.KEY_D)) {
			players[playerNum].x += 5;
		}

		if (input.isKeyDown(Input.KEY_W)) {
			players[playerNum].y -= 5;
		}

		if (input.isKeyDown(Input.KEY_S)) {
			players[playerNum].y += 5;
		}

	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.fillRect(players[0].x, players[0].y, 50, 50);
		g.fillRect(players[1].x, players[1].y, 50, 50);

	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SlickClient());

		app.setAlwaysRender(true);
		app.setTargetFrameRate(30);
		app.setDisplayMode(800, 600, false);
		app.start();
	}
}

class ClientThread extends Thread implements Runnable {
	
	Socket socket;
	Player[] players;
	int playerNum;
	ObjectOutputStream out;
	ObjectInputStream in;

	public ClientThread(Player[] players) {
		super("ClientThread");

		try {

			socket = new Socket("localhost", 4444);
			socket.setTcpNoDelay(true);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			this.players = players;
			playerNum = in.readInt();
			System.out.println(playerNum);

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (true) {
				if (playerNum == 0) {
					try {
						out.writeInt(players[0].x);
						out.writeInt(players[0].y);
						out.flush();
						players[1].x = in.readInt();
						players[1].y = in.readInt();
					}

					catch (Exception e) {
						e.printStackTrace();
					}
				}

				else if (playerNum == 1) {
					try {
						out.writeInt(players[1].x);
						out.writeInt(players[1].y);
						out.flush();
						players[0].x = in.readInt();
						players[0].y = in.readInt();
					}

					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
}