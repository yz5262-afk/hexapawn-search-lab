import java.util.Vector;
import java.util.Random;
/**
 * This is a program that creates the AIPlayer class
 * @author Zijie Mu
 * @author Jonathan Zhang
 */
public class AIPlayer extends AbstractPlayer {
    // intelligence level of the AI player
    private int depth;
    // pick a random children
    private static Random rand = new Random();

    public static void setSeed(long seed) { rand.setSeed(seed); }

    /**
     * Constructor
     * initializes the player's name, color and depth
     */
    public AIPlayer(String name, char color, int depth) {
        this.name = name;
        this.color = color;
        if (depth < 1) throw new IllegalArgumentException("Search depth must be positive");
        this.depth = depth;
    }

    /**
     * @param board the Hexboard object that the game is based on
     */
    @Override
    protected HexMove selectMove(HexBoard board) {
        GameTree tree = new GameTree(board, color, depth);

        //select the best move
        Vector<HexMove> best = tree.bestMoves();
        if (best.isEmpty()) return null;

        // Randomly select one of the best-scoring moves
        return best.get(rand.nextInt(best.size()));
    }
}