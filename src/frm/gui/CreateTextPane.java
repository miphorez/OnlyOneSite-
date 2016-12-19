package frm.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class CreateTextPane extends JPanel {
    private JTextPane jTextPane;

    public CreateTextPane(boolean modeOpaque) {
        super(new BorderLayout());
        Border lBorder;
        lBorder = new LineBorder(new Color(132, 132, 132));
        setBorder(lBorder);
        setPreferredSize(new Dimension(240,500));
        setMinimumSize(new Dimension(240,50));

        jTextPane = new JTextPane();
        DefaultCaret caret = (DefaultCaret) jTextPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jTextPane.setFont(new Font("Tahoma",Font.PLAIN,11));
        jTextPane.setEditable(false);
        jTextPane.setOpaque(modeOpaque);
        jTextPane.setContentType("text/html");

        JScrollPane scrollPane = new JScrollPane(jTextPane);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        EmptyBorder emptyBorder = new EmptyBorder(3, 3, 3, 3);
        if (modeOpaque) scrollPane.setBackground(Color.white);
        scrollPane.setBorder(emptyBorder);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTextPane getjTextPane() {
        return jTextPane;
    }
}
