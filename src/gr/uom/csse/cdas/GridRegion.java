package gr.uom.csse.cdas;

import java.io.Serializable;

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
 * A GridRegion. Instances of the class represent GridRegions which is
 * the super-class of Grid, Square and GridRegionContainer instances.
 * This class is abstract.
 * public abstract class GridRegion implements Serializable
 */
public abstract class GridRegion implements Serializable {

    static final long serialVersionUID = -6060018597481040532L;

    private GridRegionGroup group;
    private Object key;
    protected int row; // row index. In a Grid this symbolizes number of rows
    protected int column; // column index. In a Grid this symbolizes number of columns
    protected GridRegion parent;
    protected int sX, sY, mX, mY;
    private int[] id = null;

    protected GridRegion(GridRegion parent, int row, int column) {
        if (parent == null)return;
        //System.out.println(row + " " + column);
        int[] parentId = parent.id();
        int columns = 0;
        if (parent instanceof GridRegionContainer) {
            columns = ((GridRegionContainer) parent).subcolumns();
            id = new int[parentId.length + 1];
            System.arraycopy(parentId, 0, id, 0, parentId.length);
        } else if (parent instanceof Grid) {
            columns = ((Grid) parent).subcolumns();
            id = new int[1];
        }
        id[id.length - 1] = (column + 1) + (row * columns);
    }

    protected void setGridRegionGroup(GridRegionGroup group, Object key) {
        this.group = null;
        this.key = null;
    }

    /**
     * Returns the row index of a Grid Region. In a Grid where it doesn't have
     * a row index it returns the number of rows of the Grid.
     * @return int row index of Region in parent Region or Grid.
     */
    public int getRow() {
        return row;
    }

    /**
    * Returns the column index of a Grid Region. In a Grid where it doesn't have
    * a column index it returns the number of rows of the Grid.
    * @return int column index of Region in parent Region or Grid.
    */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the parent region. In a Grid where it doesn't have a parent
     * region it returns null.
     * @return GridRegion parent region
     * @see GridRegion
     */
    public GridRegion getParent() {
        return parent;
    }

    protected int[] id() {
        return id;
    }

    protected void setRegion(int sX, int sY, int mX, int mY) {
        this.sX = sX;
        this.sY = sY;
        this.mX = mX;
        this.mY = mY;
    }

    /**
     * Returns the starting width pixel index of the region.
     * @return int starting width pixel index
     */
    public int startingWidth() {
        return sX;
    }

    /**
     * Returns the ending width pixel index of the region.
     * @return int ending width pixel index
     */
    public int maxWidth() {
        return mX;
    }

    /**
     * Returns the starting height pixel index of the region.
     * @return int starting height pixel index
     */
    public int startingHeight() {
        return sY;
    }

    /**
     * Returns the ending height pixel index of the region.
     * @return int ending height pixel index pixel
     */
    public int maxHeight() {
        return mY;
    }

    public boolean equals(Object o) {
        GridRegion r;
        try {
            r = (GridRegion) o;
            return equals(r);
        } catch (ClassCastException e) {
            return false;
        }
    }

    /**
     * Compares two regions.
     * @param r GridRegion the second GridRegion to compare to
     * @return boolean
     */
    public boolean equals(GridRegion r) {
        if (id == null || r.id() == null)
            throw new RuntimeException("Cannot compare Grids to Grid Regions");
        if (id.length != r.id().length)
            return false;
        for (int i = 0; i < id.length; i++) {
            if (id[i] != r.id()[i])
                return false;
        }
        return true;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < id.length; i++) {
            s = s + " " + id[i];
        }
        return s;
    }
}
