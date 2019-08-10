package gr.uom.csse.cdas;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

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
 */

class DrawPanel extends JPanel {

    private BufferedImage buf;
    private Grid grid;
    public int WIDTH;
    public int HEIGHT;
    private String login = "unnamed";

    public DrawPanel(boolean drawable, int width, int height) {
        super();
        WIDTH = width;
        HEIGHT = height;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        buf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g2d = buf.createGraphics();
        g2d.setColor(Color.blue);
        g2d.setBackground(Color.white);
        if (drawable) {
            addMouseMotionListener(new MouseMotionListener() {
                public void mouseDragged(MouseEvent e) {
                    g2d.fillRect(e.getPoint().x, e.getPoint().y, CDASPanel.STROKE_DEPTH, CDASPanel.STROKE_DEPTH);
                    grid.mark(e.getPoint().x, e.getPoint().y);
                    updateUI();
                    if (CDASPanel.DEBUG)
                        printPass();
                }

                public void mouseMoved(MouseEvent e) {
//              g2d.drawRect(e.getPoint().x,e.getPoint().y,1,1);
//              updateUI();
                }
            });
            addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {

                }

                public void mouseEntered(MouseEvent e) {

                }

                public void mouseExited(MouseEvent e) {

                }

                public void mousePressed(MouseEvent e) {
                    g2d.fillRect(e.getPoint().x, e.getPoint().y, CDASPanel.STROKE_DEPTH, CDASPanel.STROKE_DEPTH);
                    grid.mark(e.getPoint().x, e.getPoint().y);
                    updateUI();
                    //printPass();
                }

                public void mouseReleased(MouseEvent e) {
                    if (CDASPanel.PENUP)
                        grid.penUp();
                    updateUI();
                    if (CDASPanel.DEBUG)
                        printPass();
                }
            });
        }
    }

    public void printPass() {
        System.out.println("--- PRINT DRAW SEQUENCE ----------");
        Square[] s = grid.markedSequence();
        for (int i = 0; i < s.length; i++) {
            System.out.println(s[i]);
        }
        System.out.println("--- END DRAW SEQUENCE ----------");
        System.out.println(CDASUtils.toHexString(CDASUtils.hash(s)));
    }

    public Image getImage() {
        return buf;
    }

    public void save(String name) {
        CDASUtils.saveJPG(buf, name + ".jpg", WIDTH, HEIGHT);
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
        drawGrid();
    }

    public Grid getGrid() {
        return grid;
    }

    public void drawGrid() {
        Graphics2D g2d = (Graphics2D) buf.getGraphics();
        g2d.setColor(Color.black);
        g2d.setBackground(Color.white);
        g2d.clearRect(0, 0, WIDTH, HEIGHT);
//        g2d.drawRect(0, 0, WIDTH, 1);
//        g2d.drawRect(0, 0, 1, HEIGHT);
//        g2d.drawRect(0, HEIGHT - 2, WIDTH, 1);
//        g2d.drawRect(WIDTH - 2, 0, 1, HEIGHT);
        grid.drawGrid(g2d, WIDTH, HEIGHT);
        updateUI();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buf, 0, 0, this);
        Graphics2D g2d = (Graphics2D) buf.getGraphics();
    }

    private void jbInit() throws Exception {
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String login() {
        return login;
    }
}

