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
 * @version 0.61
 */
public class MFrame extends JFrame {

    public static final String VERSION = "0.61";

    JPanel contentPane;
    BorderLayout borderLayout1 = new BorderLayout();
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JMenu jMenuHelp = new JMenu();
    JMenuItem jMenuHelpAbout = new JMenuItem();
    JLabel statusBar = new JLabel();
    BorderLayout borderLayout2 = new BorderLayout();
    private CDASPanel dPanel;
    JMenu jMenuOption = new JMenu();
    JRadioButtonMenuItem createBut = new JRadioButtonMenuItem();
    JRadioButtonMenuItem compareBut = new JRadioButtonMenuItem();
    JRadioButtonMenuItem retrieveBut = new JRadioButtonMenuItem();
    JRadioButtonMenuItem saveBut = new JRadioButtonMenuItem();
    ButtonGroup group = new ButtonGroup();
    JMenuItem jMenuFileTemplate = new JMenuItem();
    Grid[] templates;

    private int[] d = {2, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2};
    private int[] d2 = {2, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    public MFrame() {
        group.add(saveBut);
        group.add(createBut);
        group.add(compareBut);
        group.add(retrieveBut);
        // init templates
        templates = new Grid[11];
        templates[0] = Templates.template1();
        templates[1] = Templates.template2();
        templates[2] = Templates.standardTemplate(5, 5);
        templates[3] = Templates.standardTemplate(3, 3);
        templates[4] = Templates.standardTemplate(3, 4);
        templates[5] = Templates.template3();
        templates[6] = Templates.raquetTemplate();
        templates[7] = Templates.reverseRaquetTemplate();
        templates[8] = Templates.bricksTemplate();
        templates[9] = Templates.extendedBricksTemplate();
        templates[10] = Templates.crossTemplate();
        String id = JOptionPane.showInputDialog(this,"Login");
        dPanel = new CDASPanel(id);
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        //dPanel.setGrid(Templates.template1());
        //dPanel.setGrid(Templates.template2());
        //dPanel.setGrid(Templates.standardTemplate(5,5));
        dPanel.setGrid(
            Templates.crossTemplate());
    }

    public CDASPanel getCDASPanel() {
        return dPanel;
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(borderLayout1);
        setSize(new Dimension(400, 300));
        setTitle("Complex DAS Prototype");
        statusBar.setText(" ");
        jMenuFile.setText("File");
        jMenuFileExit.setText("Exit");
        jMenuFileExit.addActionListener(new MFrame_jMenuFileExit_ActionAdapter(this));
        jMenuHelp.setText("Help");
        jMenuHelpAbout.setText("About");
        jMenuHelpAbout.addActionListener(new
                                         MFrame_jMenuHelpAbout_ActionAdapter(this));
        jMenuOption.setText("Options");
        createBut.setText("Password creation");
        createBut.addActionListener(new MFrame_createBut_actionAdapter(this));
        compareBut.setText("Password Comparisson");
        compareBut.addActionListener(new MFrame_compareBut_actionAdapter(this));
        retrieveBut.setText("Password retrieval");
        retrieveBut.setToolTipText("");
        retrieveBut.addActionListener(new MFrame_retrieveBut_actionAdapter(this));
        saveBut.setText("Save as JPG");
        saveBut.addActionListener(new MFrame_saveBut_actionAdapter(this));
        jMenuFileTemplate.setText("Template");
        jMenuFileTemplate.addActionListener(new
                MFrame_jMenuFileTemplate_actionAdapter(this));
        jMenuBar1.add(jMenuFile);
        jMenuBar1.add(jMenuOption);
        jMenuFile.add(jMenuFileTemplate);
        jMenuFile.add(jMenuFileExit);
        jMenuBar1.add(jMenuHelp);
        jMenuHelp.add(jMenuHelpAbout);
        setJMenuBar(jMenuBar1);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        jMenuOption.add(createBut);
        jMenuOption.add(compareBut);
        jMenuOption.add(retrieveBut);
        jMenuOption.add(saveBut);
        contentPane.add(dPanel, BorderLayout.CENTER);
    }

    /**
     * File | Exit action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Help | About action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuHelpAbout_actionPerformed(ActionEvent actionEvent) {
        MFrame_AboutBox dlg = new MFrame_AboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.pack();
        dlg.show();
    }

    public void createBut_actionPerformed(ActionEvent actionEvent) {
        String id = JOptionPane.showInputDialog(this,"Login");
        dPanel.setLogin(id);
        dPanel.setMode(CDASPanel.CREATE_PASS_MODE);
    }

    public void compareBut_actionPerformed(ActionEvent actionEvent) {
        String id = JOptionPane.showInputDialog(this,"Login");
        dPanel.setLogin(id);
        dPanel.setMode(CDASPanel.COMPARE_PASS_MODE);
    }

    public void retrieveBut_actionPerformed(ActionEvent actionEvent) {
        String id = JOptionPane.showInputDialog(this,"Login");
        dPanel.setLogin(id);
        dPanel.setMode(CDASPanel.RETRIEVE_PASS_MODE);
    }

    public void saveBut_actionPerformed(ActionEvent actionEvent) {
        String id = JOptionPane.showInputDialog(this,"Login");
        dPanel.setLogin(id);
        dPanel.setMode(CDASPanel.SAVE_JPEG_MODE);
    }

    public void jMenuFileTemplate_actionPerformed(ActionEvent actionEvent) {
        MFrame_Template dlg = new MFrame_Template(this, "Choose a Template", true, templates);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.pack();
        dlg.show();
    }
}


class MFrame_jMenuFileTemplate_actionAdapter implements ActionListener {
    private MFrame adaptee;
    MFrame_jMenuFileTemplate_actionAdapter(MFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuFileTemplate_actionPerformed(actionEvent);
    }
}


class MFrame_saveBut_actionAdapter implements ActionListener {
    private MFrame adaptee;
    MFrame_saveBut_actionAdapter(MFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.saveBut_actionPerformed(actionEvent);
    }
}


class MFrame_retrieveBut_actionAdapter implements ActionListener {
    private MFrame adaptee;
    MFrame_retrieveBut_actionAdapter(MFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.retrieveBut_actionPerformed(actionEvent);
    }
}


class MFrame_compareBut_actionAdapter implements ActionListener {
    private MFrame adaptee;
    MFrame_compareBut_actionAdapter(MFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.compareBut_actionPerformed(actionEvent);
    }
}


class MFrame_createBut_actionAdapter implements ActionListener {
    private MFrame adaptee;
    MFrame_createBut_actionAdapter(MFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.createBut_actionPerformed(actionEvent);
    }
}


class MFrame_jMenuFileExit_ActionAdapter implements ActionListener {
    MFrame adaptee;

    MFrame_jMenuFileExit_ActionAdapter(MFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuFileExit_actionPerformed(actionEvent);
    }
}


class MFrame_jMenuHelpAbout_ActionAdapter implements ActionListener {
    MFrame adaptee;

    MFrame_jMenuHelpAbout_ActionAdapter(MFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuHelpAbout_actionPerformed(actionEvent);
    }
}
