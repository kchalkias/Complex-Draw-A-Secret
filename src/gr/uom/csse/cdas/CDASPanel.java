package gr.uom.csse.cdas;

import java.io.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;

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
 * A Complex-DAS Panel. This Panel is the main class of the CDAS implementation.
 * public class CDASPanel extends JPanel
 */
public class CDASPanel extends JPanel {

    public static final int DEFAULT_WIDTH = 800; // 240
    public static final int DEFAULT_HEIGHT = 600; // 284
    public static final int STROKE_DEPTH = 2; // in pixels
    public static final int PASS_LIMIT = 5;
    public static final boolean PENUP = true;
    public static final boolean DEBUG = true;

    public static final int CREATE_PASS_MODE = 0;
    public static final int COMPARE_PASS_MODE = 1;
    public static final int RETRIEVE_PASS_MODE = 2;
    public static final int SAVE_JPEG_MODE = 3;
    private int mode = 0;

    DrawPanel drawPanel = new DrawPanel(true, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    JPanel bPanel = new JPanel();
    JButton okButton = new JButton();
    JButton cancButton = new JButton();
    BorderLayout borderLayout1 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();

    private byte[] originalPass = null;

    /**
     * Constructs a CDAS Panel.
     * @param login String login-id
     */
    public CDASPanel(String login) {
        setLogin(login);
        mode = SAVE_JPEG_MODE;
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action();
            }
        });
        cancButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        init();
    }

    /**
     * Sets the mode of the panel. Available modes are:
     * CREATE_PASS_MODE, COMPARE_PASS_MODE, RETRIEVE_PASS_MODE, SAVE_JPEG_MODE
     * @param mode int mode of operation of panel
     */
    public void setMode(int mode) {
        this.mode = mode;
        originalPass = null;
        init();
    }

    private void init() {
        switch(mode) {
        case CREATE_PASS_MODE:
            break;
        case COMPARE_PASS_MODE:
            try {
                readPassword();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Could not retrieve password",JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Could not retrieve password",JOptionPane.ERROR_MESSAGE);
            }

            break;
        case RETRIEVE_PASS_MODE:
            try {
                readPassword();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Could not retrieve password",JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Could not retrieve password",JOptionPane.ERROR_MESSAGE);
            }

            break;
        case SAVE_JPEG_MODE:
            break;
        }
    }

    private void action() {
        switch (mode) {
        case CREATE_PASS_MODE:
            try {
                savePassword();
            } catch (IOException e) {
               JOptionPane.showMessageDialog(this, e.getMessage(), "Could not save password",JOptionPane.ERROR_MESSAGE);
            }
            break;

        case COMPARE_PASS_MODE:
            if (comparePasswords())
                JOptionPane.showMessageDialog(this, "Correct password", "Password matched",JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Incorrect password", "Could not match password",JOptionPane.ERROR_MESSAGE);
            break;

        case RETRIEVE_PASS_MODE:
            System.out.println(drawPanel.getGrid());
            System.out.println(CDASUtils.toHexString(originalPass));
            break;

        case SAVE_JPEG_MODE:
            drawPanel.save(drawPanel.login() +
                           (int) (Math.random() * 100000));
            break;
        }
    }

    /**
     * Appoint a Grid to the panel.
     * @param grid Grid a Grid
     * @return boolean Sucess of operation
     */
    public boolean setGrid(Grid grid) {
        boolean result = false;
        switch(mode) {
        case CREATE_PASS_MODE:
            drawPanel.setGrid(grid);
            result = true;
            break;
        case COMPARE_PASS_MODE:
            break;
        case RETRIEVE_PASS_MODE:
            break;
        case SAVE_JPEG_MODE:
            drawPanel.setGrid(grid);
            result = true;
            break;
        }
        return result;
    }

    /**
     * If the panel is operating in COMPARE_PASS_MODE compare the pass sequence
     * drawn on the panel to the stored pass-sequence.
     * @return boolean
     */
    public boolean comparePasswords() {
        byte[] pass = CDASUtils.hash(drawPanel.getGrid().markedSequence());
        if (originalPass == null)
            return false;
        if (pass.length != originalPass.length)
            return false;
        for (int i = 0; i < pass.length; i++) {
            if (pass[i] != originalPass[i])
                return false;
        }
        return true;
    }

    /**
     * Clear Grid.
     */
    public void clear() {
        drawPanel.getGrid().clearGrid();
        drawPanel.drawGrid();
    }

    /**
     * Change the login-id
     * @param login String login-id
     */
    public void setLogin(String login) {
        drawPanel.setLogin(login);
    }

    /**
     * Save Grid instance and SHA-1 hash of pass-sequence in a file. The file
     * is named "login".dat where "login" is the login-id set on the panel.
     * @throws IOException
     */
    public void savePassword() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(
                drawPanel.login() + ".cdas"));
        Square[] sequence = drawPanel.getGrid().markedSequence();
        if (sequence.length < PASS_LIMIT) {
            JOptionPane.showMessageDialog(this, "Small password",
                                          "Error! Password not saved",
                                          JOptionPane.ERROR);
            return;
        }
        byte[] pass = CDASUtils.hash(sequence);
        drawPanel.getGrid().clearGrid();
        os.writeObject(drawPanel.getGrid());
        os.writeObject(pass);
        os.close();
        JOptionPane.showMessageDialog(this, "Password saved", "Password saved",
                                      JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Loads a Grid instance and SHA-1 hash of a pass-sequence from a file.
     * The login-id set on the panel is used to select the filename.
     * @throws IOException
     * @throws ClassCastException
     * @throws ClassNotFoundException
     */
    public void readPassword() throws IOException, ClassCastException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(drawPanel.login()+".cdas"));
        Grid g = (Grid) is.readObject();
        originalPass = (byte[]) is.readObject();
        drawPanel.setGrid(g);
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        bPanel.setLayout(flowLayout1);
        okButton.setText("OK");
        cancButton.setText("Cancel");
        drawPanel.setBackground(Color.white);
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT+36));
        this.setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT+36));
        bPanel.add(cancButton);
        bPanel.add(okButton);
        this.add(drawPanel, java.awt.BorderLayout.CENTER);
        this.add(bPanel, java.awt.BorderLayout.SOUTH);
    }
}
