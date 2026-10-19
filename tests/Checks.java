import java.util.*;

/** Independent board-recursion oracle for the expected-outcome tree policy. */
public class Checks {
    static int checks=0, losingPositions=0;
    static void require(boolean condition,String message) {
        checks++;if(!condition)throw new AssertionError(message);
    }
    static double value(HexBoard board,char next,char perspective,int depth) {
        char last=HexBoard.opponent(next);
        if(board.win(last))return last==perspective?1:-1;
        if(depth==0)return 0;
        Vector<HexMove> moves=board.moves(next);
        double sum=0;
        for(HexMove m:moves)sum+=value(new HexBoard(board,m),last,perspective,depth-1);
        return moves.isEmpty()?0:sum/moves.size();
    }
    static String key(HexMove move) {return move.from()+":"+move.to();}
    static void inspect(HexBoard board,char color,Set<String> seen) {
        String state=board.toString()+color;
        if(!seen.add(state)||board.win(HexBoard.opponent(color)))return;
        Vector<HexMove> legal=board.moves(color);
        if(legal.isEmpty())return;
        for(int depth:new int[]{1,2,4,6}) {
            double best=Double.NEGATIVE_INFINITY;
            Set<String> expected=new HashSet<>();
            for(HexMove move:legal) {
                double score=value(new HexBoard(board,move),HexBoard.opponent(color),color,depth-1);
                if(score>best) {best=score;expected.clear();expected.add(key(move));}
                else if(score==best)expected.add(key(move));
            }
            Set<String> actual=new HashSet<>();
            for(HexMove move:new GameTree(board,color,depth).bestMoves())actual.add(key(move));
            require(actual.equals(expected),"Tree choices differ from oracle at depth "+depth);
            require(!actual.isEmpty(),"A playable position must retain a move");
            if(best<0)losingPositions++;
        }
        for(HexMove move:legal)inspect(new HexBoard(board,move),HexBoard.opponent(color),seen);
    }
    public static void main(String[] args) {
        HexBoard opening=new HexBoard();
        require(opening.moves(HexBoard.WHITE).size()==3,"Initial white moves");
        require(opening.moves(HexBoard.BLACK).size()==3,"Initial black moves");
        String before=opening.toString();
        new HexBoard(opening,opening.moves(HexBoard.WHITE).firstElement());
        require(opening.toString().equals(before),"Child construction mutated parent board");
        Set<String> seen=new HashSet<>();inspect(opening,HexBoard.WHITE,seen);
        require(losingPositions>0,"Oracle checks must cover forced-negative expected scores");
        AIPlayer.setSeed(42);
        AIPlayer player=new AIPlayer("AI",HexBoard.WHITE,4);
        require(player.selectMove(opening)!=null,"AI must choose a legal opening move");
        boolean rejected=false;try{new AIPlayer("AI",HexBoard.WHITE,0);}catch(IllegalArgumentException e){rejected=true;}
        require(rejected,"Nonpositive AI depth must be rejected");
        System.out.printf("Passed %d assertions over %d reachable board/turn states; %d negative-score cases.%n",checks,seen.size(),losingPositions);
    }
}
