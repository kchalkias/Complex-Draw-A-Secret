package gr.uom.csse.cdas;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

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
 * Based on AgentGroup from the "A Library for Developing Multi-Agent Systems"
 * (c) 2003-2004 Tasos Alexiadis
 * ---------------------------------------------------------------------------
 * A GridRegionGroup. Instances of the class cand hold Grid Regions.
 * public class GridRegionGroup implements Serializable
 */

public class GridRegionGroup implements Serializable {

    static final long serialVersionUID = -4302408023086153798L;

    private Class GridRegionType = null;
    private HashMap group = null;
    private boolean locked = false;

    private static Class GridRegionContainerType = null;
    private static Class SquareType = null;

    static {
        try {
            Class GridRegionContainerType = Class.forName("gr.uom.csse.cdas.GridRegionContainer");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("GridRegionClass class not found!");

        }
        try {
            Class SquareType = Class.forName("gr.uom.csse.cdas.Square");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SquareClass class not found!");
        }
    }

    public GridRegionGroup(Class GridRegionTypeClass) {
        group = new HashMap();
        setGridRegionType(GridRegionTypeClass);
    }

    public GridRegionGroup(String GridRegionClassPackage) {
        group = new HashMap();
        try {
            Class c = Class.forName(GridRegionClassPackage);
            setGridRegionType(c);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("GridRegionClass class not found!");
        }
    }

    protected GridRegionGroup() {
        group = new HashMap();
    }

    // Do this in the constructor of the GridRegionGroup,define what GridRegions does this group hold
    protected void setGridRegionType(Class GridRegionClass) {
        if (GridRegionType != null)
            throw new UnsupportedOperationException(
                    "The GridRegion type for this GridRegion group has already been set");
        GridRegionType = GridRegionClass;
    }

    public Class getGridRegionType() {
        return GridRegionType;
    }

    public synchronized void addGridRegion(GridRegion a, Object key) {
        if (locked)
            throw new UnsupportedOperationException(
                    "GridRegion Group locked");
        if (GridRegionType == null)
            throw new UnsupportedOperationException(
                    "This GridRegion Group type has not been set");
        if (!GridRegionType.equals(a.getClass()))
            throw new IllegalArgumentException(
                    "A is not of the same type as " + GridRegionType);
        group.put(key, a);
        a.setGridRegionGroup(this, key);
    }

    public synchronized void removeGridRegion(Object key) {
        GridRegion a;
        if (locked)
            throw new UnsupportedOperationException(
                    "GridRegion Group locked");
        if (group.containsKey(key)) {
            a = (GridRegion) group.get(key);
            group.remove(key);
            a.setGridRegionGroup(null, null);
        }
    }

    /**
     * Returns all Grid Regions in the group.
     * @return GridRegion[] grid regions in the group
     * @see GridRegion
     */
    public synchronized GridRegion[] getGridRegions() {
        Object[] ags = group.values().toArray();
        GridRegion[] a = new GridRegion[ags.length];
        for (int i = 0; i < ags.length; i++) {
            a[i] = (GridRegion) ags[i];
        }
        return a;
    }

    protected synchronized void clear() {
        group.clear();
    }

    public synchronized boolean containsGridRegion(GridRegion a) {
        if (group.containsValue(a))
            return true;
        else
            return false;
    }

    public synchronized GridRegion getGridRegion(Object key) {
        return (GridRegion) group.get(key);
    }

    public synchronized void markAllSquares() {
        if (!GridRegionType.equals(SquareType))
           throw new IllegalArgumentException(
                   "This operation only works on Square types");
        Square a;
        Iterator it;
        it = group.values().iterator();
        while (it.hasNext()) {
            a = (Square) it.next();
            a.mark();
        }
    }

    public synchronized void clearAllSquares() {
        if (!GridRegionType.equals(SquareType))
            throw new IllegalArgumentException(
                    "This operation only works on Square types");
        Square a;
        Iterator it;
        it = group.values().iterator();
        while (it.hasNext()) {
            a = (Square) it.next();
            a.erase();
        }
    }

    public synchronized void lock() {
        locked = true;
    }

    protected synchronized void unlock() {
        locked = false;
    }

    public synchronized int size() {
        return group.size();
    }

    public String toString() {
        return "GridRegion Group of " + GridRegionType.getPackage().getName() +
                " GridRegions";
    }
}

