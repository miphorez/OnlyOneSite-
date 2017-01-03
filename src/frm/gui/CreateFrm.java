package frm.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static utils.ConstantForAll.PROGRAM_TITLE;
import static utils.ConstantForAll.PROGRAM_VERSION;
import static utils.UtilsForAll.setCustomIconForProgram;

public class CreateFrm extends JFrame {
    private JPanel jpMain;
    private JPanel jpMainBorder;
    private JPanel jpContainerMain;
    private boolean flVisibleFrm;

    public CreateFrm(){
        setCustomIconForProgram(this);
        setTitle(PROGRAM_TITLE + " (v" + PROGRAM_VERSION + ")");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        flVisibleFrm = true;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                flVisibleFrm = false;
            }
        });

        jpMain = new JPanel(new FlowLayout());
        jpMainBorder = new JPanel(new BorderLayout());
        jpContainerMain = new JPanel(new CardLayout());
        jpContainerMain.setBorder(new EmptyBorder(3,5,3,5));
        jpMainBorder.add(jpContainerMain, BorderLayout.CENTER);
        setContentPane(jpMainBorder);

        jpMain = BoxLayoutUtils.createVerticalPanel();
    }

    public JPanel getJpMain() {
        return jpMain;
    }

    public JPanel getJpContainerMain() {
        return jpContainerMain;
    }

    public void viewModalFrm(int hightFrm, int widthFrm, boolean modeVisible) {
        pack();
        setBounds(0, 0, hightFrm, widthFrm);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(modeVisible);
    }

    public boolean isFlVisibleFrm() {
        return flVisibleFrm;
    }

    public void setFlVisibleFrm(boolean flVisibleFrm) {
        this.flVisibleFrm = flVisibleFrm;
    }
}
