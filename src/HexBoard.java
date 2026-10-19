import java.util.Vector;
import java.util.Random;
import java.util.Iterator;
import java.util.Scanner;

/**
 * A class for representing a HexAPawn board.
 *
 * @author Duane A. Bailey
 * @author Jon
 */
public class HexBoard{
  private char[] board;
  private int rows, cols;
  private Vector<HexMove> whiteMoves, blackMoves;
  public final static char WHITE = 'O';
  public final static char BLACK = 'X';
  public final static char SPACE = ' ';

  /**
   * constructs a standard hexapawn board
   */
  public HexBoard(){
    this(3,3);
  }

  /**
   * constructs a hexapawn board, white on top, black on bottom
   *
   * @param r number of rows >= 3
   * @param c number of columns >= 3
   */
  public HexBoard(int r, int c){
    rows = r;
    cols = c;

    board = new char[rows*cols];
    for (int pos = 0; pos < rows*cols; pos++){
      if (pos < cols) 
        board[pos] = WHITE;
      else if (pos >= (rows-1)*cols) 
        board[pos] = BLACK;
      else 
        board[pos] = SPACE;
    }

    whiteMoves = blackMoves = null;
  }

  /**
   * constructs a new hexapawn board derived from orig after m
   *
   * @param orig a valid original board
   * @param m a valid move
   */
  public HexBoard(HexBoard orig, HexMove m){
    rows = orig.rows;
    cols = orig.cols;

    board = new char[rows*cols];
    for (int i = 0; i < rows*cols; i++) 
      board[i] = orig.board[i];

    board[m.to()] = board[m.from()];
    board[m.from()] = SPACE;

    whiteMoves = blackMoves = null;
  }

  /**
   * returns the color of the opponent player
   *
   * @param c player color (WHITE or BLACK)
   *
   * @return opponent color
   */
  public static char opponent(char c){
    return (c == WHITE)? BLACK: WHITE;
  }

  /**
   * checks whether Player c has won the game
   *
   * @param c player color (WHITE or BLACK)
   *
   * @return true if c has won, false otherwise.
   */
  public boolean win(char c){
    if (c == WHITE){
      for (int pos = (rows-1)*cols; pos < rows*cols; pos++){
        if (board[pos] == WHITE) return true;
      }

      return 0 == moves(BLACK).size();
    }else{
      for (int pos = 0; pos < cols; pos++){
        if (board[pos] == BLACK) 
          return true;
      }

      return 0 == moves(WHITE).size();
    }
  }

  /**
   * returns a vector of possible hexapawn moves from this
   * position, provided c moves next
   *
   * @param c player color (WHITE or BLACK)
   *
   * @return a vector of possible moves
   */
  public Vector<HexMove> moves(char c){
    if (c == WHITE){
      if (whiteMoves != null) 
        return whiteMoves;

      whiteMoves = new Vector<HexMove>();
      for (int pos = 0; pos < (rows-1)*cols; pos++){
        if (board[pos] == WHITE){
          if (board[pos+cols] == SPACE)
            whiteMoves.addElement(new HexMove(pos,pos+cols,cols));

          if ((pos%cols)!=0 && board[pos+(cols-1)] == BLACK)
            whiteMoves.addElement(new HexMove(pos,pos+cols-1,cols));

          if ((pos%cols)!=(cols-1) && board[pos+cols+1] == BLACK)
            whiteMoves.addElement(new HexMove(pos,pos+cols+1,cols));
        }
      }

      return whiteMoves;
    }else{
      if (blackMoves != null) 
        return blackMoves;

      blackMoves = new Vector<HexMove>();
      for (int pos = cols; pos < rows*cols; pos++){
        if (board[pos] == BLACK) {
          if (board[pos-cols] == SPACE)
            blackMoves.addElement(new HexMove(pos,pos-cols,cols));

          if ((pos%cols)!=0 && board[pos-cols-1] == WHITE)
            blackMoves.addElement(new HexMove(pos,pos-cols-1,cols));

          if ((pos%cols)!=cols-1 && board[pos-cols+1] == WHITE)
            blackMoves.addElement(new HexMove(pos,pos-cols+1,cols));
        }
      }

      return blackMoves;
    }
  }

  /** 
   * returns a printable version of the board.
   *
   * @return string representation of the board
   */
  public String toString(){
    String result = "-";
    for (int pos = 0; pos < cols; pos++) 
      result += "--";
    result += "\n";

    for (int pos = 0; pos < rows*cols; pos++) {
      result += " "+((char)board[pos]);
      if ((cols-1) == (pos%cols)) 
        result += '\n';
    }

    for (int pos = 0; pos < cols; pos++) 
      result += "--";
    result += "-\n\n";

    return result;
  }
}
