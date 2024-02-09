package The_Game;

import Messages.InputReader;
import Players.Player;
import Tile.Position;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


public class CLI {
    public Messages.MessageCallback m;
    public Messages.InputReader r;
    //private GameController gameController;
    
    public CLI(){
        //this.gameController = gameController;
        m = (s) -> displayMessage(s);
        r = () -> readLine();
    }

    public void displayMessage(String m){
        System.out.println(m);
    }
    public void displayBoard(String m){
        System.out.print(m);
    }


    public String readLine(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
