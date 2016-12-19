package frm.gui;

import javax.swing.*;
import java.awt.*;

public class CreateCheckBox extends JCheckBox {

    public CreateCheckBox(int sizeX, int sizeY, int horText, int horAlign, String text) {
        super();
        setFocusPainted(false);
        if ((sizeX != 0)&&(sizeY != 0)) {
            setPreferredSize(new Dimension(sizeX, sizeY));
            setMaximumSize(new Dimension(sizeX, sizeY));
            setMinimumSize(new Dimension(sizeX, sizeY));
        }
        setFont(new Font("Tahoma",Font.PLAIN,11));
        setHorizontalTextPosition(horText);
        setHorizontalAlignment(horAlign);
        setText("<html>"+text);
    }
}
