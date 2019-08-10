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
 * A GridRegionContainer. Instances of this class represent Grid regions
 * which contain sub-regions.
 * public class GridRegionContainer extends GridRegion
 */
public class GridRegionContainer extends GridRegion {

    private int subrows;
    private int subcolumns;
    private int squares;
    private boolean containsSubRegions = false;
    private boolean subRegionsSet = false;
    private GridRegion[][] children = null;
    private Square thisAsSquare = null;

    protected GridRegionContainer(GridRegion parent, int row, int column) {
        super(parent, row, column);
        this.row = row;
        this.column = column;
        this.parent = parent;
        thisAsSquare = new Square(parent, row, column, this);
    }

    protected void createSubRegions(int rows, int columns, boolean containsSubRegions) {
        if (rows == 0 || columns == 0)
            throw new IllegalArgumentException("rows and columns cannot be zero");
        if (subRegionsSet)
            throw new UnsupportedOperationException("Sub regions already created");
        this.containsSubRegions = containsSubRegions;
        this.subrows = rows;
        this.subcolumns = columns;
        squares = subrows * subcolumns;
        children = new GridRegionContainer[subrows][subcolumns];
        subRegionsSet = true;
        if (containsSubRegions) {
            for (int i = 0; i < subrows; i++) {
                for (int k = 0; k < subcolumns; k++) {
                    children[i][k] = new GridRegionContainer(this, i, k);
                }
            }
        } else {
            children = new Square[subrows][subcolumns];
            for (int i = 0; i < subrows; i++) {
                for (int k = 0; k < subcolumns; k++) {
                    children[i][k] = new Square(this, i, k);
                }
            }
        }
    }

    /**
     * Returns number of rows in the GridRegionContainer.
     * @return int number of rows
     */
    public int subrows() {
        if (!subRegionsSet)
            throw new IllegalArgumentException("region not set");
        return subrows;
    }

    /**
    * Returns number of columns in the GridRegionContainer.
    * @return int number of columns
    */
    public int subcolumns() {
        if (!subRegionsSet)
            throw new IllegalArgumentException("region not set");
        return subcolumns;
    }

    /**
     * Returns number of sub-regions or squares in the region.
     * @return int number of sub-regions or squares.
     */
    public int squares() {
        return squares;
    }

    /**
     * Returns sun-regions.
     * @return GridRegion[][] sub-regions
     * @see GridRegion
     */
    public GridRegion[][] getChildren() {
        return children;
    }

    /**
     * Returns a sub-region.
     * @param row int the row index of a region in the container
     * @param column int the column index a region in the container
     * @return GridRegion the region specified
     * @see GridRegion
     */
    public GridRegion getChild(int row, int column) {
        return children[row][column];
    }

    /**
     * Checks if this container contains sub regions.
     * @return boolean
     */
    public boolean containsSubRegions() {
        return containsSubRegions;
    }

    /**
     * Returns square representation of container.
     * @return Square a Square instance representing this region
     * @see Square
     */
    public Square asSquare() {
        return thisAsSquare;
    }

    protected void setRegion(int sX, int sY, int mX, int mY) {
       super.setRegion(sX, sY, mX, mY);
       thisAsSquare.setRegion(sX, sY, mX, mY);
    }

    public String toString() {
        return "GridRegionContainer: ( " + row + ", " + column + " )" + " contains " + "( " + subrows + ", " + subcolumns + " )\n" + super.toString();
    }

}
