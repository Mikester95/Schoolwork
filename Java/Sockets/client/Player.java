package client;

import java.io.*;
import user.*;

public class Player {
    
    protected static int evaluate(char c1, char c2) {
        if (c1 == c2) {
            System.out.println("You and your opponent both picked " + c1 + ". It's a tie! Let's play again!");
            return 0;
        }
        System.out.println("You picked " + c1);
        System.out.println("Your opponent picked " + c2);
        char[] order = new char[3];
        order[0] = 'R';
        order[1] = 'P';
        order[2] = 'S';
        for (int i = 0; i < 3; ++i) {
            int current = i;
            int next = (i+1)%3;
            if (c1 == order[current] && c2 == order[next]) {
                System.out.println("You lose");
                return -1;
            }
        }
        System.out.println("You win");
        return 1;
    }
    
    protected User user;
    
    public Player(User user) {
        this.user = user;
    }
    
    protected char chooseMove() throws IOException {
        while(true) {
            System.out.println("Choose between rock paper and scissors. \n"
                    + "Type R for Rock.\n"
                    + "Type P for Paper.\n"
                    + "Type S for Scissors.\n");;
            char choice = (Client.getInstance().readString()).charAt(0);
            if (choice == 'R' || choice == 'P' || choice == 'S') {
                return choice;
            }
        }
    }
    
    public void play() throws IOException {
        System.out.println("Playing");
        int result = 0;
        while (result == 0) {
            result = makeMove(chooseMove());
        }
        
        endGame(result);
    }
    
    protected void registerLoss() {
        user.modifyRating(-100);
        System.out.format("Your current rating is %d\n", user.getRating());
    }
    
    protected void registerWin() {
        user.modifyRating(100);
        System.out.format("Your current rating is %d\n", user.getRating());
    }
    
    protected int makeMove(char choice) throws IOException {
        return 0;
    }
    
    protected void endGame(int result) throws IOException {
        if (result == -1) {
            registerLoss();
        } else {
            registerWin();
        }
    }
    
}
