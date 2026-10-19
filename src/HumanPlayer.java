import java.util.Vector;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
/**
 * This is a program that creates HumanPlayer class
 * @author Jonathan Zhang
 * @author Zijie Mu
 */
public class HumanPlayer extends AbstractPlayer {
    private Scanner sc;

    /**
     * Constructor
     * Constructs human player object
     * @param name: name of the player
     * @param color: color of pawn the player is holing
     */
    public HumanPlayer(String name, char color) {
        this.name = name;
        this.color = color;
        this.sc = new Scanner(System.in);
    }

    /**
     * this method make sure each of the players can play the game
     * @param board: configuration of the game board
     * @return the current state of the game board 
     */
    protected HexMove selectMove(HexBoard board) {
        Vector<HexMove> moves = board.moves(color);

        // checks if the board is empty
        if (moves.isEmpty()) return null;

        //print all the options to move
        for (int i = 0; i < moves.size(); i++) {
            System.out.println("Option " + i + ": " + moves.get(i));
        }

        //when game not ends, ask for each player about their option and keep the board updated
        while (true) {
            System.out.print("Enter your move (0-" + (moves.size() - 1) + "): ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice >= 0 && choice < moves.size()) return moves.get(choice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
