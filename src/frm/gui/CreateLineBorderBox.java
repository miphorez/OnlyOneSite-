package frm.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CreateLineBorderBox extends JPanel {

    public CreateLineBorderBox(int sizeX, int sizeY, String title) {
        super(new BorderLayout());
        if ((sizeX != 0)&&(sizeY != 0)) {
            setPreferredSize(new Dimension(sizeX, sizeY));
            setMaximumSize(new Dimension(sizeX, sizeY));
            setMinimumSize(new Dimension(sizeX, sizeY));
        }
        LineBorder lBorder = new LineBorder(new Color(152, 152, 152));
        TitledBorder tBorder = new TitledBorder(lBorder, " "+title+" ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Tahoma",Font.PLAIN,11));
        setBorder(tBorder);
    }
}
