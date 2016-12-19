package frm.gui;

import javax.swing.*;
import java.awt.*;

public class CreateButton extends JButton {

    public CreateButton(int sizeX, int sizeY, String text) {
        super();
        if ((sizeX != 0)&&(sizeY != 0)) {
            setPreferredSize(new Dimension(sizeX, sizeY));
            setMaximumSize(new Dimension(sizeX, sizeY));
            setMinimumSize(new Dimension(sizeX, sizeY));
        }
//        Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
//        fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
//        Font fontUnderline = new Font("Tahoma",Font.PLAIN, 11).deriveFont(fontAttributes);
//        jbGoConnect.setBackground(null);
//        jbGoConnect.setOpaque(false);
//        jbGoConnect.setFocusPainted(false);
//        jbGoConnect.setContentAreaFilled(false);
//        jbGoConnect.setBorderPainted( false );
//        jbGoConnect.setMargin(new Insets(0, 0, 0, 0));
        setMargin(new Insets(0, 0, 0, 0));
        setFont(new Font("Tahoma",Font.PLAIN, 11));
        setText("<html>"+text);
    }
}
