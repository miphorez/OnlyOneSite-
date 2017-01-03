package frm.gui;

import frm.password.EInsideCmd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreateButton extends JButton {
    private Color btnTextColor;
    private GUIMessenger guiMessenger = new GUIMessenger();

    public CreateButton(int sizeX, int sizeY, String text) {
        super();
        if ((sizeX != 0)&&(sizeY != 0)) {
            setPreferredSize(new Dimension(sizeX, sizeY));
            setMaximumSize(new Dimension(sizeX, sizeY));
            setMinimumSize(new Dimension(sizeX, sizeY));
        }
        setMargin(new Insets(0, 0, 0, 0));
        setFont(new Font("Tahoma",Font.PLAIN, 11));
        setText("<html>"+text);
        addActionListener(e ->
                guiMessenger.putCmd(EInsideCmd.PasswordField_Enter.name())
        );
    }

    public void registerListener(InsideMessenger messenger) {
        guiMessenger.addObserver(messenger.getListener());
    }

    public void setStyle_Underline() {
        setText("<html><u>"+getText());
        setBackground(null);
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted( false );
        setMargin(new Insets(0, 0, 0, 0));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnTextColor = getForeground();
                setForeground(Color.BLUE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setForeground(btnTextColor);
            }
        });
    }


}
