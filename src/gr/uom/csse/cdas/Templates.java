package gr.uom.csse.cdas;

/**
 * <p>Title: Complex DAS</p>
 *
 * <p>Description: Complex Draw-a-Secret</p>
 *
 * <p>Copyright: Copyright (c) 2006 Anastasios Alexiadis,Konstantinos Chalkias</p>
 *
 * <p>Company: University of Macedonia</p> *
 * @author Tasos Alexiadis
 * @version 0.61
 * ---------------------------------------------------------------------------
 * Templates. This class holds methods for the construction of Grids.
 * public class Templates.
 */
public class Templates {

    private Templates() {
    }

    /**
     * Create a standard Grid, like the ones in the DAS scheme.
     * @param rows int number of rows
     * @param columns int number of columns
     * @return Grid
     */
    public static Grid standardTemplate(final int rows, final int columns) {
        final Grid grid = new Grid() {
            public void createGrid() {
                row = rows;
                column = columns;
                Grid g = this;
                regions = new GridRegionContainer[row][column];
                for (int i = 0; i < row; i++) {
                    for (int k = 0; k < column; k++) {
                        regions[i][k] = new GridRegionContainer(g, i, k);
                        ((GridRegionContainer) regions[i][k]).
                                createSubRegions(
                                        1, 1, false);
                    }
                }

            }
        };
        return grid;
    }

    /**
     * Creates a nested Grid.
     * @param rows int[] number of rows from main region to subs
     * @param columns int[] number of columns from main regions to subs
     * @return Grid
     */
    public static Grid newCustomTemplate(final int[] rows, final int[] columns) {
        if (rows.length != columns.length)
            throw new IllegalArgumentException();
        if (rows.length < 2)
            throw new IllegalArgumentException();
        final int sq = rows[0] * columns[0];
        if ((rows.length - 1) < sq)
            throw new IllegalArgumentException();
        final Grid grid = new Grid() {
            public void createGrid() {
                int[] squarev = calcSquares(rows, columns);
                int reg = 0;
                int[] boundaries;
                boolean sub;
                row = rows[0];
                column = columns[0];
                Grid g = this;
                regions = new GridRegionContainer[row][column];
                for (int i = 0; i < row; i++) {
                    for (int k = 0; k < column; k++) {
                        boundaries = getSubBoundaryIndeces(squarev, reg + 1);
                        sub = (squarev.length > boundaries[1] - 1);
                        regions[i][k] = new GridRegionContainer(g, i, k);
                        ((GridRegionContainer) regions[i][k]).createSubRegions(rows[
                                reg + 1], columns[reg + 1], sub);
                        if (sub)
                            createSubRegions(((GridRegionContainer) regions[i][k]).
                                             getChildren(), rows,
                                             columns, squarev, reg + 1);
                        reg++;
                    }
                }
            }
        };
        return grid;
    }

    private static void createSubRegions(GridRegion[][] regions, int[] rows, int[] columns, int[] squarev, int index) {
        GridRegionContainer g;
        int[] boundaries = getSubBoundaryIndeces(squarev, index);
        int[] chboundaries;
        boolean sub;
        int reg = 0;
        for (int i = 0; i < rows[index]; i++) {
            for (int k = 0; k < columns[index]; k++) {
                if (regions[i][k] instanceof GridRegionContainer) {
                    g = (GridRegionContainer) regions[i][k];
                    chboundaries = getSubBoundaryIndeces(squarev,
                            boundaries[0] + reg);
                    sub = (squarev.length > chboundaries[1] - 1);
                    g.createSubRegions(rows[boundaries[0] + reg],
                                       columns[boundaries[0] + reg],
                                       sub);
                    if (sub)
                        createSubRegions(g.getChildren(), rows, columns, squarev,
                                         boundaries[0] + reg);
                    reg++;

                }

            }
        }
    }
    // [x,y] := where x = starting index and y = ending index + 1
    private static int[] getSubBoundaryIndeces(int[] squares, int index) {
        int[] b = new int[2];
        if (index > squares.length - 1) {
            b[0] = squares.length;
            b[1] = squares.length;
            return b;
        }
        int c = 0;
        for (int i = 0; i < index; i++) {
            c += squares[i];
        }
        c++;
        b[0] = c;
        b[1] = c + squares[index];
        return b;
    }

    private static int[] calcSquares(int[] rows, int[] columns) {
        int[] sq = new int[rows.length];
        for (int i = 0; i < rows.length; i++) {
            sq[i] = rows[i] * columns[i];
        }
        return sq;
    }

    /**
     * A pre-defined template.
     * @return Grid
     */
    public static Grid template1() {
        int[] rows = {2, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] columns = {2, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1};
        return newCustomTemplate(rows, columns);
    }

    /**
     * A pre-defined template.
     * @return Grid
     */
    public static Grid template2() {
        int[] rows = {2, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3,
                     1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1,
                     1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1,
                     1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2,
                     2, 2, 2, 1, 1, 1,
                     1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] columns = {2, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1,
                        3, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1,
                        1,
                        1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1,
                        1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2,
                        2, 2, 2, 2, 2, 1, 1, 1,
                        1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1};

        return newCustomTemplate(rows, columns);
    }

    /**
     * A pre-defined template.
     * @return Grid
     */
    public static Grid template3() {
        int[] rows = {2, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3,
                     1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1,
                     1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1,
                     1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3,
                     3, 3, 3, 1, 1, 1,
                     1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] columns = {2, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1,
                        3,
                        1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1,
                        1,
                        1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 3, 3, 3, 3, 3,
                        3,
                        3, 3, 3, 1, 1, 1,
                        1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1};
        return newCustomTemplate(rows, columns);
    }

    /**
     * A pre-defined template.
     * @return Grid
     */
    public static Grid raquetTemplate() {
        int[] rows = {5, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 2, 4, 2, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1};
        int[] columns = {5, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 2, 4, 2, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1};
        return newCustomTemplate(rows, columns);
    }

    /**
     * A pre-defined template.
     * @return Grid
     */
    public static Grid reverseRaquetTemplate() {
        int[] rows = {5, 4, 1, 1, 1, 4, 1, 2, 2, 2, 1, 1, 2, 1, 2, 1, 1, 2, 2, 2, 1, 4, 1, 1, 1, 4};
        int[] columns = {5, 4, 1, 1, 1, 4, 1, 2, 2, 2, 1, 1, 2, 1, 2, 1, 1, 2, 2, 2, 1, 4, 1, 1, 1, 4};
        return newCustomTemplate(rows, columns);
    }

    /**
     * A pre-defined template.
     * @return Grid
     */
   public static Grid bricksTemplate() {
       int[] rows = {3, 1, 1, 1};
       int[] columns = {1, 4, 3, 4};
       return newCustomTemplate(rows, columns);
   }

   /**
    * A pre-defined template.
    * @return Grid
    */
  public static Grid extendedBricksTemplate() {
      int[] rows = {3, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1};
      int[] columns = {1, 4, 3, 4, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1};
      return newCustomTemplate(rows, columns);
  }


  /**
   * A pre-defined template.
   * @return Grid
   */
  public static Grid crossTemplate() {
      int[] rows = {3, 1, 2, 1, 2, 1, 2, 1, 2, 1};
      int[] columns = {3, 1, 2, 1, 2, 1, 2, 1, 2, 1};
      return newCustomTemplate(rows, columns);
  }
}
