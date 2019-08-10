package gr.uom.csse.cdas;

import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Image;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import java.awt.Graphics2D;
import com.sun.image.codec.jpeg.JPEGCodec;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

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
 * A collection of utilities.
 * public class CDAUtils
 */
public class CDASUtils {
    private CDASUtils() {
    }

    public static void saveJPG(Image img, String s, int width, int height) {
        BufferedImage bi = new BufferedImage
                           (width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(img, 0, 0, null);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(s);
        } catch (java.io.FileNotFoundException io) {
            System.out.println("File Not Found");
        }

        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
        param.setQuality(0.5f, false);
        encoder.setJPEGEncodeParam(param);

        try {
            encoder.encode(bi);
            out.close();
        } catch (java.io.IOException io) {
            System.out.println("IOException");
        }
    }

    /**
     * Hashes a pass sequence.
     * @param sequence Square[] pass sequence
     * @return byte[] SHA-1 hash
     */
    public static byte[] hash(Square[] sequence) {
        String sequenceID = "";
        int[] id = null;
        for (int i = 0; i < sequence.length; i++) {
            if (i > 0)
                    sequenceID = sequenceID + "-";
            if (sequence[i] instanceof PenUpAction) {
                sequenceID = sequenceID + "PU";
            } else {
                id = sequence[i].id();
                for (int k = 0; k < id.length; k++) {
                    if (i == 0 && k == 0)
                        sequenceID = id[0] + "";
                    else if (k == 0)
                        sequenceID = sequenceID + id[k];
                    else
                        sequenceID = sequenceID + "," + id[k];
                }
            }
        }
        if (CDASPanel.DEBUG)
            System.out.println(sequenceID);
        return hash(sequenceID.getBytes());
    }

    public static byte[] hash(byte[] msg) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            sha.update(msg);
            return sha.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /*
     * Converts a byte to hex digit and writes to the supplied buffer
     * (Taken from the JCE API Documentation)
     */
    private static void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                          '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    /*
     * Converts a byte array to hex string
     * (Taken from the JCE API Documentation)
     */
    public static String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();

        int len = block.length;

        for (int i = 0; i < len; i++) {
            byte2hex(block[i], buf);
            if (i < len - 1) {
                buf.append(":");
            }
        }
        return buf.toString();
    }
}
