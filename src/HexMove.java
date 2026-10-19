/**
 * A class for representing a HexAPawn move
 *
 * @author Duane A. Bailey
 * @author Jon
 */
public class HexMove{
    private int from;
    private int to;
    private int cols;

    /**
     * construct a new move from position f to position t
     * on a board with c cols
     *
     * @param f from position
     * @param t to position
     * @param c number of columns
     */
    public HexMove(int f, int t, int c){
	from = f;
	to = t;
	cols = c;
    }

    /**
     * returns the from position
     *
     * @return from position
     */
    public int from(){
	return from;
    }

    /**
     * returns the to position
     *
     * @return to position
     */
    public int to(){
	return to;
    }

    /**
     * generates printable representation of move
     *
     * @return string representation of the move
     */
    public String toString(){
        return "Move from ["+(1+(from/cols))+","+(1+(from%cols))+"] "+
	    "to ["+(1+(to/cols))+","+(1+(to%cols))+"].";
    }
}
