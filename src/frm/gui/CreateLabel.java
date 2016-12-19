package frm.gui;

import javax.swing.*;
import java.awt.*;

public class CreateLabel extends JLabel {

    public CreateLabel(int sizeX, int sizeY, int horText, int horAlign, String text) {
        super();
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

    public void setFontColor(Color color) {
        setForeground(color);
    }
}
