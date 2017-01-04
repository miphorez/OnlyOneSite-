package frm.gui;

import xml.preset.ETContent;

import javax.swing.*;
import java.awt.*;

public class CreateComboBox extends JComboBox<ETContent> {

    public CreateComboBox(int sizeX, int sizeY) {
        super();
        setBasicParam(sizeX, sizeY);
    }

    private void setBasicParam(int sizeX, int sizeY) {
        setFocusable(false);
        if ((sizeX != 0)&&(sizeY != 0)) {
            setPreferredSize(new Dimension(sizeX, sizeY));
            setMaximumSize(new Dimension(sizeX, sizeY));
            setMinimumSize(new Dimension(sizeX, sizeY));
        }
        setFont(new Font("Tahoma",Font.PLAIN,11));
    }
}
