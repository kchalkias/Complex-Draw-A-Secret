package gr.uom.csse.cdas;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;

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
 * A Template Panel. This Panel is used to choose a grid from a list
 * of templates.
 * public class TemplatePanel extends JPanel implements MouseListener
 */
public class TemplatePanel extends JPanel implements MouseListener {

    JPanel bPanel = new JPanel();
    JButton cancButton = new JButton();
    JButton okButton = new JButton();
    BorderLayout borderLayout1 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    JPanel cPanel = new JPanel();
    DrawPanel[] templates;
    Grid[] grids;
    DrawPanel current = null;

    /**
     * Constructs template panel
     * @param grids Grid[] templates to be displayed
     */
    public TemplatePanel(Grid[] grids) {
        this.grids = grids;
        init();
        for (int i = 0; i < templates.length; i++) {
            cPanel.add(templates[i]);
            templates[i].addMouseListener(this);
        }
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // Init all Templates
    private void init() {
        templates = new DrawPanel[grids.length];
        for (int i = 0; i < grids.length; i++) {
            templates[i] = new DrawPanel(false, 50, 50);
            templates[i].setGrid(grids[i]);
        }
    }

    private void setTemplate(DrawPanel c) {
        current = c;
    }

    /**
     * Returns selected Grid.
     * @return Grid selected Grid
     */
    public Grid getGrid() {
        if (current != null)
            return current.getGrid();
        else
            return null;
    }

    /**
     * Add an action to the OK button.
     * @param listener ActionListener
     */
    public void addokaction(ActionListener listener) {
        okButton.addActionListener(listener);
    }

    /**
     * Add an action to the Cancel button.
     * @param listener ActionListener
     */
    public void addcancaction(ActionListener listener) {
        cancButton.addActionListener(listener);
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        cancButton.setText("Cancel");
        okButton.setText("OK");
        bPanel.add(cancButton);
        bPanel.add(okButton);
        this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(cPanel);
        this.add(bPanel, java.awt.BorderLayout.SOUTH);
    }

    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < templates.length; i++) {
            if (e.getSource() == templates[i])
                setTemplate(templates[i]);
        }
    }
    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }
}
