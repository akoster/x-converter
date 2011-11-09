package xcon.randomstranger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws IOException {
        
        RandomStranger randomStranger = new RandomStranger(System.out);
        
        String userName = "user";
        BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        while ((userInput = user.readLine()) != null) {
            System.out.println(String.format("%s : %s", userName, userInput));
            randomStranger.receive(userName, userInput);
        }
    }
}
