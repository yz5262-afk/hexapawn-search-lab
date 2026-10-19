/** Reproducible portfolio CLI for the original CMSC 221 Hexapawn project. */
public class Driver {
    public static void main(String[] args) {
        if (args.length>0 && args[0].equals("human")) {
            Player human=new HumanPlayer("Human",HexBoard.WHITE);
            Player ai=new AIPlayer("Search AI",HexBoard.BLACK,4);
            Player winner=human.play(new HexBoard(),ai);
            System.out.println(winner+" wins!");
            return;
        }
        int games=args.length>0?Integer.parseInt(args[0]):20;
        int whiteDepth=args.length>1?Integer.parseInt(args[1]):4;
        int blackDepth=args.length>2?Integer.parseInt(args[2]):1;
        long seed=args.length>3?Long.parseLong(args[3]):42;
        if (games<1) throw new IllegalArgumentException("Game count must be positive");
        AIPlayer.setSeed(seed);
        int whiteWins=0;
        for(int game=0;game<games;game++) {
            HexBoard board=new HexBoard();
            char color=HexBoard.WHITE;
            AIPlayer white=new AIPlayer("White",HexBoard.WHITE,whiteDepth);
            AIPlayer black=new AIPlayer("Black",HexBoard.BLACK,blackDepth);
            for(int turn=0;turn<100;turn++) {
                AIPlayer current=color==HexBoard.WHITE?white:black;
                HexMove move=current.selectMove(board);
                if(move==null) { if(color==HexBoard.BLACK)whiteWins++; break; }
                board=new HexBoard(board,move);
                if(board.win(color)) { if(color==HexBoard.WHITE)whiteWins++; break; }
                color=HexBoard.opponent(color);
                if(turn==99)throw new IllegalStateException("Game did not terminate");
            }
        }
        System.out.printf("games=%d whiteDepth=%d blackDepth=%d seed=%d%n",games,whiteDepth,blackDepth,seed);
        System.out.printf("White wins: %d%nBlack wins: %d%n",whiteWins,games-whiteWins);
    }
}
