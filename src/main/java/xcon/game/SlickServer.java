package xcon.game;

import java.io.IOException;
import java.net.ServerSocket;

public class SlickServer{
    public static void main(String[] args) throws IOException {

        int MAX_PLAYERS = 3;
        int playerNum = 0;
        Player[] players = new Player[MAX_PLAYERS];
        players[0] = new Player(25,25);
        players[1] = new Player(125,125);
        players[2] = new Player(225,225);
        ServerSocket serverSocket = new ServerSocket(4444);
        boolean listening = true;

        while(listening){
            System.out.println("Waiting to connect with: " + playerNum);
            new ServerThread(serverSocket.accept(), players, playerNum).start();
            //stops here.
            System.out.println("Connected with: " + playerNum + " Now incrementing");
            playerNum++;
            System.out.println("Incremented to: " + playerNum);
        }



        serverSocket.close();
        System.exit(0);
    }
}