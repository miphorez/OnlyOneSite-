package frm.addcontent;

import frm.gui.InsideListener;
import frm.password.EInsideCmd;

import java.util.Observable;
import java.util.Observer;

public class GUIListenerAddContent extends InsideListener implements Observer {
    private FrmAddContent frmAddContent;

    public GUIListenerAddContent(FrmAddContent frmAddContent) {
        this.frmAddContent = frmAddContent;
    }

    @Override
    public void update(Observable o, Object arg) {
        String strCmd = (String)arg;
        switch (EInsideCmd.getCmdByStr(strCmd)){
            case TextField_Enter:
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
    }

    private void cmdReset() {
    }
}
