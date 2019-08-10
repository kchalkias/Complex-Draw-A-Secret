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
 * @version 0.4
 */
public class Area implements Serializable {

    static final long serialVersionUID = -3076200301443254243L;

    private int sX;
    private int sY;
    private int mX;
    private int mY;

    public Area(int sX, int sY, int mX, int mY) {
        this.sX = sX;
        this.sY = sY;
        this.mX = mX;
        this.mY = mY;
    }

    public int startingWidth() {
        return sX;
    }

    public int startingHeight() {
        return sY;
    }

    public int maxWidth() {
        return mX;
    }

    public int maxHeight() {
        return mY;
    }
}
