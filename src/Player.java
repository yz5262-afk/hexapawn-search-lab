/**
 * An interface for a player of hexapawn.
 *
 * @author Duane A. Bailey
 */
public interface Player {
    // make sure your constructor accepts a char (HexBoard.WHITE or
    // HexBoard.BLACK) to play with.  This should be remembered.

    /**
     * a method to make a move and to call play() on the opponent. 
     * The recursion ends by returning the winning player when the game ends.
     *
     * @param board a non-null game board
     * @param opponent the player to play after this player
     *
     * @return winning Player is returned 
     */
    public Player play(HexBoard board, Player opponent);
}

