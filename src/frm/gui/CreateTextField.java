package frm.gui;

import frm.password.EInsideCmd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.prefs.Preferences;

import static frm.gui.GUIUtils.getBackgroundTextFildColor;
import static utils.PrefParam.PREF_PASS_PRESET;
import static utils.UtilsForAll.getMD5String;

public class CreateTextField extends JTextField {
    private GUIMessenger guiMessenger = new GUIMessenger();

    public CreateTextField() {
        super();
        setPreferredSize(new Dimension(100, 18));
        setMaximumSize(new Dimension(500, 18));
        setMinimumSize(new Dimension(200, 18));
        setBackground(getBackgroundTextFildColor());
        addKeyListener(new TextFieldKeyAdapter());
        addFocusListener(new TextFieldFocusAdapter());
        setText("");
    }

    public void registerListener(InsideMessenger messenger) {
        guiMessenger.addObserver(messenger.getListener());
    }

    class TextFieldFocusAdapter extends FocusAdapter {
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

    class TextFieldKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            if (e.getKeyCode() == 27) {
                guiMessenger.putCmd(EInsideCmd.TextField_Exit.name());
                goFromTextField();
            }
            if (e.getKeyCode() == 10) {
                guiMessenger.putCmd(EInsideCmd.TextField_Enter.name());
                goFromTextField();
            }
        }
    }


    private void goFromTextField() {
        setEscapeBackground();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
    }

    private void setEscapeBackground() {
        setBackground(getBackgroundTextFildColor());
    }

}

