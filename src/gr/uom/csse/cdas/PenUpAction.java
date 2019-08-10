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
 * A PenUpAction. Instances of this class represent pen up actions which can
 * used as part of a pass sequence. All PenUpActions when checked for equality
 * between them return true and cannot be marked like squares.
 * public class PenUpAction extends Square
 */

public class PenUpAction extends Square {

    protected PenUpAction() {
        super(null, 0, 0);
    }

    public void mark() {

    }

    public void erase() {

    }

    public boolean equals(Object o) {
        PenUpAction p;
        try {
            p = (PenUpAction) o;
            return equals(p);
        } catch (ClassCastException e) {
            return false;
        }
    }

    public boolean equals(GridRegion r) {
        PenUpAction p;
        try {
            p = (PenUpAction) r;
            return equals(p);
        } catch (ClassCastException e) {
            return false;
        }
    }

    public boolean equals(PenUpAction p) {
        return true;
    }

    public String toString() {
        return "Pen Up";
    }
}
