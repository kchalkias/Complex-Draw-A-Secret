package gr.uom.csse.cdas;

/**
 * <p>Title: Complex DAS</p>
 *
 * <p>Description: Complex Draw-a-Secret</p>
 *
 * <p>Copyright: Copyright (c) 2006 Anastasios Alexiadis,Konstantinos Chalkias</p>
 *
 * <p>Company: University of Macedonia</p>
 *
 * @author Tasos Alexiadis
 * @version 0.52
 * ---------------------------------------------------------------------------
 * A Square. Instances of this class represent end squares which can be marked
 * and used as part of a pass sequence.
 * public class Square extends GridRegion
 */
public class Square extends GridRegion {

    private int marked;
    private GridRegionContainer thisAsContainer = null;

    protected Square(GridRegion parent, int row, int column) {
        super(parent, row, column);
        this.parent = parent;
        marked = 0;
        this.row = row;
        this.column = column;
    }

    protected Square(GridRegion parent, int row, int column, GridRegionContainer thisAsContainer) {
        super(parent, row, column);
        this.parent = parent;
        marked = 0;
        this.row = row;
        this.column = column;
        this.thisAsContainer = thisAsContainer;
    }

    /**
     * Checks if this squere has been marked.
     * @return boolean
     */
    public boolean isMarked() {
        return (marked > 0)? true : false;
    }

    /**
     * Increments the number of marks in the square by one.
     */
    public void mark() {
        marked++;
    }

    /**
     * Decreases the number of marks in the square by one.
     */
    public void erase() {
        marked--;
    }

    /**
     * Returns the mark count of the square.
     * @return int mark count
     */
    public int markCount() {
        return marked;
    }

    /**
     * Checks if the square is the square representation of a container.
     * @return boolean
     */
    public boolean isContainer() {
        if (thisAsContainer == null)
            return false;
        else
            return true;
    }

    /**
     * If this square is the square representation of a container it returns
     * the container. Otherwise it returns null.
     * @return GridRegionContainer container representation of square if the
     * square is actually a container.
     */
    public GridRegionContainer asContainer() {
        return thisAsContainer;
    }

    public String toString() {
        return "Square:" + super.toString() + "( " + startingWidth() + ", " +
                startingHeight() + " ) - "
                + "( " + maxWidth() + ", " + maxHeight() + " ) mark times: " +
                marked;
    }
}
