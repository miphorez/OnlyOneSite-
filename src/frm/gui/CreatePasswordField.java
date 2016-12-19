package frm.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observer;
import java.util.prefs.Preferences;

import static frm.gui.GUIUtils.getBackgroundTextFildColor;
import static utils.UtilsForAll.getPrefString;

public class CreatePasswordField extends JPasswordField {
    private String strForPref, strForPreset;
    private JLabel jlForMessage;

    public CreatePasswordField(String strForPref, String strForPreset,
                               char echo,
                               JLabel jlForMessage) {
        super(10);
        setPreferredSize(new Dimension(100, 18));
        setMaximumSize(new Dimension(500, 18));
        setMinimumSize(new Dimension(200, 18));
        setEchoChar(echo);
        this.strForPref = strForPref;
        this.strForPreset = strForPreset;
        this.jlForMessage = jlForMessage;
        setBackground(getBackgroundTextFildColor());
        addKeyListener(new PasswordKeyAdapter());
        addFocusListener(new PasswordFocusAdapter());
        String iStr = getPrefString(strForPref, strForPreset);
        setText(iStr);
    }



    class PasswordFocusAdapter extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            super.focusGained(e);
            setBackground(Color.white);
        }

        @Override
        public void focusLost(FocusEvent e) {
            super.focusLost(e);
            setEscapeBackground();
        }
    }

    class PasswordKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            if (e.getKeyCode() == 27) {
                setText(getPrefString(strForPref, strForPreset));
                goFromTextField();
            }
            if (e.getKeyCode() == 10) {
                goControlTextField();
            }
        }
    }

    public void goControlTextField() {
        String iStr = String.valueOf(getPassword());
        if (iStr.length() < 4) {
            jlForMessage.setText("Слишком короткий пароль");
        } else goSaveTextField();
    }

    private void goSaveTextField() {
        String iStr = String.valueOf(getPassword());
        setText(iStr);
        Preferences.userRoot().put(strForPref, iStr);
        jlForMessage.setText("Пароль изменен");
        goFromTextField();
    }

    private void goFromTextField() {
        setEscapeBackground();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
    }

    private void setEscapeBackground() {
        setBackground(getBackgroundTextFildColor());
    }

}

