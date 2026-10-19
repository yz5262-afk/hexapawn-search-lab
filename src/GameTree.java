import java.util.Vector;
import java.util.Random;
import java.util.Iterator;
import java.util.Scanner;

public class GameTree {
    private HexBoard board;
    private char currentPlayer;
    private char lastPlayer;
    private Vector<GameTree> children;

    public GameTree(HexBoard board, char currentPlayer, int depth) {
        if (depth < 0) throw new IllegalArgumentException("Depth must be nonnegative");
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.lastPlayer = HexBoard.opponent(currentPlayer);
        children = new Vector<>();

        if (board.win(lastPlayer) || depth == 0) return;

        Vector<HexMove> moves = board.moves(currentPlayer);
        for (int i = 0; i < moves.size(); i++) {
            HexMove move = moves.get(i);
            HexBoard newBoard = new HexBoard(board, move);
            children.add(new GameTree(newBoard, lastPlayer, depth - 1));
        }
    }

    private double evaluate(GameTree tree) {
        if (tree.children.isEmpty()) {
            if (tree.board.win(tree.lastPlayer)) return (tree.lastPlayer == currentPlayer) ? 1 : -1;
            return 0;
        }
        double sum = 0;
        for (GameTree child : tree.children) sum += evaluate(child);
        return sum / tree.children.size();
    }

    public Vector<HexMove> bestMoves() {
        Vector<HexMove> moves = board.moves(currentPlayer);
        Vector<HexMove> best = new Vector<>();
        if (board.win(lastPlayer)) return best;
        if (children.isEmpty()) return new Vector<>(moves);
        if (moves.isEmpty()) return best;

        double bestScore = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < children.size(); i++) {
            double score = evaluate(children.get(i));
            if (score > bestScore) {
                bestScore = score;
                best.clear();
                best.add(moves.get(i));
            } else if (score == bestScore) {
                best.add(moves.get(i));
            }
        }
        return best;
    }
}