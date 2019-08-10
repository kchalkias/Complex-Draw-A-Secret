package gr.uom.csse.cdas;

import java.awt.*;
import java.awt.event.*;
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
 * @version 0.6
 */
public class MFrame_Template extends JDialog {
    TemplatePanel panel1;
    BorderLayout borderLayout1 = new BorderLayout();
    MFrame par;

    public MFrame_Template(Frame frame, String string, boolean _boolean, Grid[] grids) {
        super(frame, string, _boolean);
        panel1 = new TemplatePanel(grids);
        par = (MFrame) frame;
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        panel1.addokaction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Grid grid = panel1.getGrid();
                dispose();
                if (grid != null)
                    par.getCDASPanel().setGrid(grid);
            }
        });
        panel1.addcancaction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public MFrame_Template() {
        this(new Frame(), "MFrame_Template", false, null);
    }

    private void jbInit() throws Exception {
        panel1.setMinimumSize(new Dimension(400, 400));
        panel1.setPreferredSize(new Dimension(400, 400));
        this.getContentPane().add(panel1, BorderLayout.CENTER);

    }
}
