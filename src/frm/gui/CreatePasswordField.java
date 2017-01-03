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

public class CreatePasswordField extends JPasswordField {
    private String strForPref;
    private String strTempPassword;
    private GUIMessenger guiMessenger = new GUIMessenger();

    public CreatePasswordField(String strForPref, char echo) {
        super(10);
        setPreferredSize(new Dimension(100, 18));
        setMaximumSize(new Dimension(500, 18));
        setMinimumSize(new Dimension(200, 18));
        setEchoChar(echo);
        this.strForPref = strForPref;
        setBackground(getBackgroundTextFildColor());
        addKeyListener(new PasswordKeyAdapter());
        addFocusListener(new PasswordFocusAdapter());
        setText("");
    }

    public void registerListener(InsideMessenger messenger) {
        guiMessenger.addObserver(messenger.getListener());
    }

    public String getKeyPref() {
        return strForPref;
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
                guiMessenger.putCmd(EInsideCmd.PasswordField_Exit.name());
            }
            if (e.getKeyCode() == 10) {
                guiMessenger.putCmd(EInsideCmd.PasswordField_Enter.name());
            }
        }
    }

    public EStatusPasswordControl goControlTextField(EStatusPasswordControl goStatus) {
        String iStr = String.valueOf(getPassword());
        switch (goStatus){
            case GO_CONTROL:
                return goControlPassword(iStr);
            case GO_NEW_PASSWORD:
                strTempPassword = iStr;
                return goControlNewPassword(iStr);
            case GO_NEW_PASSWORD_CONFIRM:
                return goConfirmNewPassword(iStr);
        }
        return EStatusPasswordControl.NONE;
    }

    private EStatusPasswordControl goConfirmNewPassword(String iPass) {
        if (iPass.length() < 4) return EStatusPasswordControl.TOO_SHORT;
        if (!Objects.equals(iPass, strTempPassword)) return EStatusPasswordControl.WRONG;
        return EStatusPasswordControl.OK;
    }

    private EStatusPasswordControl goControlNewPassword(String iPass) {
        if (iPass.length() < 4) return EStatusPasswordControl.TOO_SHORT;
        return EStatusPasswordControl.OK;
    }

    private EStatusPasswordControl goControlPassword(String iPass) {
        if (iPass.length() < 4) return EStatusPasswordControl.TOO_SHORT;
        String strFromPref = Preferences.userRoot().get(strForPref, PREF_PASS_PRESET);
        if (!Objects.equals(strFromPref, PREF_PASS_PRESET)) iPass = getMD5String(iPass);
        return Objects.equals(iPass, strFromPref) ?
                EStatusPasswordControl.OK : EStatusPasswordControl.WRONG;
    }

//    private void goFromTextField() {
//        setEscapeBackground();
//        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
//    }

    private void setEscapeBackground() {
        setBackground(getBackgroundTextFildColor());
    }

}

