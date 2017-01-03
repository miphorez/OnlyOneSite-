package frm.password;

import frm.gui.InsideListener;

import java.util.Observable;
import java.util.Observer;

public class GUIInsideListener extends InsideListener implements Observer {
    private FrmPassword frmPassword;

    GUIInsideListener(FrmPassword frmPassword) {
        this.frmPassword = frmPassword;
    }

    @Override
    public void update(Observable o, Object arg) {
        String strCmd = (String)arg;
        switch (EInsideCmd.getCmdByStr(strCmd)){
            case PasswordField_Enter:
                cmdEnter();
                break;
            case PasswordField_Exit:
                cmdReset();
                break;
            case NONE:
                break;
        }
    }

    private void cmdEnter() {
        frmPassword.getStepByStep().getStateSBS().goEnter();
    }

    private void cmdReset() {
        frmPassword.getStepByStep().getStateSBS().goReset();
    }
}
