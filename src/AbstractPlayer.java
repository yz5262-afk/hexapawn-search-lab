import java.util.Vector;
import java.util.Scanner;

/**
 * a partial implementation of the Player interface.
 */
public abstract class AbstractPlayer implements Player{
  /** name of the player */
  protected String name;

  /** color of the player: HexBoard.WHITE or HexBoard.BLACK*/
  protected char color;

  /**
   * select a move from the available moves
   *
   * @param board current board configuration
   * @return the chosen move
   */
  protected abstract HexMove selectMove(HexBoard board);

  /**
   * game is played from this position on; winning player is returned.
   *
   * @param board current board configuration
   * @param opponent the next player to play
   * @return the winning player
   */
  public Player play(HexBoard board, Player opponent) {
    //display the current board configuration
    System.out.println("\n"+name+" to move. Here is the board configuration:");
    System.out.print(board);

    //select move
    HexMove move = selectMove(board);
    if (move == null) {
      System.out.println("no legal moves are available for "+name);
      return opponent;
    } 
    System.out.println(name+" selects move: "+move);

    //update board
    HexBoard updatedBoard = new HexBoard(board, move);

    //possible outcome 1: the current player wins
    if (updatedBoard.win(color)){
      System.out.print(updatedBoard);
      return this;
    }

    //possible outcome 2: The opponent's turn. 
    //Note that the current player's move cannot directly result in the opponent's win.
    return opponent.play(updatedBoard,this);
  }

  /**
   * returns a String representaiton of the player, which is simply the name
   *
   * @return the name of the player
   */
  public String toString(){
    return name;
  }
}
