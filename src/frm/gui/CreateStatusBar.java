package frm.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class CreateStatusBar{
    private JLabel jlStatus;

    public CreateStatusBar(JFrame frame) {
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frame.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 20));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

        JPanel jplStatus = BoxLayoutUtils.createHorizontalPanel();
        jlStatus = new JLabel();
        jlStatus.setFont(new Font("Tahoma",Font.PLAIN,11));
        jlStatus.setForeground(new Color(116, 116, 116));
        jlStatus.setHorizontalAlignment(SwingConstants.LEFT);

        jplStatus.add(Box.createHorizontalStrut(5));
        jplStatus.add(jlStatus);

        statusPanel.add(jplStatus);
    }

    public JLabel getJlStatus() {
        return jlStatus;
    }
}
