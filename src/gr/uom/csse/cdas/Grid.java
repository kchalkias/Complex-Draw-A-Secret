package gr.uom.csse.cdas;

import java.util.ArrayList;
import java.awt.Graphics2D;

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
 * @version 0.6
 * ---------------------------------------------------------------------------
 * A Grid. Instances of the class represent Grids.
 * This class is abstract. To create a Grid use the Templates class.
 * @see Templates
 * public abstract class Grid extends GridRegion
 */
public abstract class Grid extends GridRegion {

    protected GridRegion[][] regions = null;
    protected GridRegionGroup squareGroup;
    private ArrayList markedSquares;

    public Grid() {
        super(null, 0, 0);
        squareGroup = new GridRegionGroup("gr.uom.csse.cdas.Square");
        markedSquares = new ArrayList();
    }

    public abstract void createGrid();

    /**
     * Marks a Square. The marked squere is then added to the end of the pass
     * sequence. This mehod doesn't add a square if it is the same as the last
     * marked square and no pen up action was given in between.
     * @param x int the x-coordinate of a point in the square to be marked (in pixels)
     * @param y int the y-coordinate of a point in the squere to be marked (in pixels)
     */
    public void mark(int x, int y) {
        Square s = getSquare(x, y);
        if (s == null) return;
        Square lastMarked = lastMarked();
        //System.out.println("lastmarked = " + lastMarked);
        if (lastMarked != null && lastMarked.equals(s))
            return;
        s.mark();
        markedSquares.add(s);
    }

    /**
     * Issues a pen up action and adds it at the pass sequence. It doesn't issue
     * a pen up action if the last action given was already a pen up.
     */
    public void penUp() {
        Square lastMarked = lastMarked();
        if (lastMarked != null && lastMarked instanceof PenUpAction)
            return;
        markedSquares.add(new PenUpAction());
    }

    /**
     * Clear the last square of the pass sequence. This method clears both
     * squares or pen up actions.
     */
    public void clearLast() {
        Square lastMarked = lastMarked();
        lastMarked.erase();
        markedSquares.remove(markedSquares.size() - 1);
    }

    /**
     * Clear the pass sequence.
     */
    public void clearGrid() {
        Square[] s = markedSequence();
        for (int i = 0; i < s.length; i++) {
            s[i].erase();
        }
        markedSquares.clear();
    }

    /**
     * Return the pass sequence
     * @return Square[] The pass sequence
     * @see Square
     * @see PenUpAction
     */
    public Square[] markedSequence() {
        Object[] a = markedSquares.toArray();
        Square[] squares = new Square[a.length];
        for (int i = 0; i < a.length; i++) {
            squares[i] = (Square) a[i];
        }
        return squares;
    }

    /**
     * Returns the square group containings all the end-squares of the Grid.
     * @return GridRegionGroup the group containing all the end-squares
     * @see GridRegionGroup
     */
    public GridRegionGroup squares() {
        return squareGroup;
    }

    /**
     * Returns the last marked square. This can be a Square or a PenUpAction.
     * @return Square The last marked square
     * @see Square
     * @see PenUpAction
     */
    public Square lastMarked() {
        if (markedSquares.size() > 0) {
            return (Square) markedSquares.get(markedSquares.size() - 1);
        } else
            return null;
    }

    /**
     * Returns the square containing the following coordinates.
     * @param x int the x-coordinate of a point in the square to be retrieved (in pixels)
     * @param y int the y-coordinate of a point in the square to be retrieved (in pixels)
     * @return Square The square containing the (x,y) point
     * @see Square
     */
    public Square getSquare(int x, int y) {
        Square s;
        GridRegion[] squares = squareGroup.getGridRegions();
        for (int i = 0; i < squares.length; i++) {
            s = (Square) squares[i];
            if (x > s.startingWidth() && x < s.maxWidth() &&
                y > s.startingHeight() && y < s.maxHeight())
                return s;
        }
        return null;
    }

    /**
     * Returns the main regions of the Grid.
     * @return GridRegion[][] The main regions of the Grid
     * @see GridRegion
     */
    public GridRegion[][] getMainRegions() {
        return regions;
    }

    /**
     * Returns a main region of the Grid.
     * @param row int the row index of a region in the Grid
     * @param column int the column index of a region in the Grid
     * @return GridRegion the region specified
     * @see GridRegion
     */
    public GridRegion getMainRegion(int row, int column) {
        return regions[row][column];
    }

    /**
     * Returns number of main rows in the Grid.
     * @return int number of rows
     */
    public int subrows() {
        return row;
    }

    /**
     * Returns the number of main columns in the Grid.
     * @return int number of columns
     */
    public int subcolumns() {
        return column;
    }

    private void setRegionSizes(GridRegion[][] regions, int startingWidth,
                                int startingHeight, int width, int height,
                                int rows, int columns) {
        GridRegionContainer g;
        int sW = startingWidth;
        int sH = startingHeight;
        int startingCol = (width - startingWidth) / columns;
        int startingRow = (height - startingHeight) / rows;
        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < columns; k++) {
                regions[i][k].setRegion(sW, sH, sW + startingCol,
                                        sH + startingRow);
                if (regions[i][k] instanceof GridRegionContainer) {
                    g = (GridRegionContainer) regions[i][k];
                    setRegionSizes(g.getChildren(), sW, sH, sW + startingCol,
                                   sH + startingRow, g.subrows(),
                                   g.subcolumns());
                } else if (regions[i][k] instanceof Square)
                    squareGroup.addGridRegion((Square) regions[i][k],
                                              new Area(regions[i][k].
                            startingWidth(),
                            regions[i][k].startingHeight(),
                            regions[i][k].maxWidth(),
                            regions[i][k].maxHeight()));

                sW += startingCol;
            }
            sW = startingWidth;
            sH += startingRow;
        }
    }

    protected void drawGrid(Graphics2D g2d, int width, int height) {
        if (regions == null)
            createGrid();
        squareGroup.unlock();
        squareGroup.clear();
        setRegion(0, 0, width, height);
        setRegionSizes(regions, 0, 0, width, height, row, column);
        squareGroup.lock();
        drawRegion(g2d, 0, 0, width, height, row, column);
        drawSubRegions(g2d, regions, 0, 0, width, height, row, column);
    }

    private void drawSubRegions(Graphics2D g2d, GridRegion[][] regions,
                                int startingWidth, int startingHeight,
                                int maxWidth, int maxHeight, int rows,
                                int columns) {
        GridRegionContainer g;
        int startingCol = (maxWidth - startingWidth) / columns;
        int startingRow = (maxHeight - startingHeight) / rows;
        int w, h;
        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < columns; k++) {
                if (regions[i][k] instanceof GridRegionContainer) {
                    g = (GridRegionContainer) regions[i][k];
                    w = startingWidth + (k * startingCol);
                    h = startingHeight + (i * startingRow);
                    drawRegion(g2d, w, h, w + startingCol, h + startingRow,
                               g.subrows(), g.subcolumns());
                    if (g.containsSubRegions())
                        drawSubRegions(g2d, g.getChildren(), w, h,
                                       w + startingCol,
                                       h + startingRow, g.subrows(),
                                       g.subcolumns());

                }
            }
        }
    }

    private void drawRegion(Graphics2D g2d, int startingWidth,
                            int startingHeight, int maxWidth, int maxHeight,
                            int rows, int columns) {
        int startingCol = (maxWidth - startingWidth) / columns;
        int startingRow = (maxHeight - startingHeight) / rows;
        for (int i = 0; i < columns - 1; i++)
            g2d.drawRect(((i + 1) * startingCol) + startingWidth,
                         startingHeight, 1,
                         maxHeight - startingHeight);
        for (int i = 0; i < rows - 1; i++)
            g2d.drawRect(startingWidth,
                         ((i + 1) * startingRow) + startingHeight,
                         maxWidth - startingWidth, 1);
    }

    public boolean equals(Object o) {
        Grid g;
        try {
            g = (Grid) o;
            return equals(g);
        } catch (ClassCastException e) {
            return false;
        }
    }

    public boolean equals(GridRegion r) {
        Grid g;
        try {
            g = (Grid) r;
            return equals(g);
        } catch (ClassCastException e) {
            return false;
        }
    }

    /**
     * Compares two grids.
     * @param g Grid the second Grid to compare to
     * @return boolean
     */
    public boolean equals(Grid g) {
        GridRegion[][] gRegions = g.getMainRegions();
        if (row != g.subrows() && column != g.subcolumns())
            return false;
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < column; k++) {
                if (!regions[i][k].equals(gRegions[i][k]))
                    return false;
            }
        }
        return true;
    }

    public String toString() {
        String s = "Grid - ( " + row + ", " + column + " )\n";
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < column; k++) {
                s = s + regions[i][k] + "\n";
            }
        }
        return s;
    }

}
