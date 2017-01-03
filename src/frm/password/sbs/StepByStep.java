package frm.password.sbs;

import frm.gui.CreatePasswordField;
import frm.password.FrmPassword;

import java.util.logging.Logger;

public class StepByStep {

    StateSBS sbsInputOldPassword;        //ввод старого пароля
    StateSBS sbsOkInputOldPassword;      //успешный ввод старого пароля
    StateSBS sbsErrInputPassword;        //ошибка ввода пароля
    StateSBS sbsInputNewPassword;        //ввод нового пароля
    StateSBS sbsConfirmNewPassword;      //подтверждение нового пароля
    StateSBS sbsOkNewPassword;           //успешный ввод нового пароля
    public StateSBS sbsChangePassword;   //изменить пароль
    StateSBS sbsEscape;                  //сброс ввода пароля
    private StateSBS stateSBS;

    private Logger logger;
    private FrmPassword frmPassword;
    private boolean flOk;
    private boolean flChangePassword;

    public StepByStep(Logger log, FrmPassword frmPassword) {
        logger = log;
        this.frmPassword = frmPassword;
        logger.info("StepByStep Password: start");
        sbsInputOldPassword = new SBSInputOldPassword(this);
        sbsOkInputOldPassword = new SBSOkInputOldPassword(this);
        sbsErrInputPassword = new SBSErrInputPassword(this);
        sbsInputNewPassword = new SBSInputNewPassword(this);
        sbsConfirmNewPassword = new SBSConfirmNewPassword(this);
        sbsOkNewPassword = new SBSOkNewPassword(this);
        sbsChangePassword = new SBSChangePassword(this);
        sbsEscape = new SBSEscape(this);
    }

    public void goStart(){
        stateSBS = sbsInputOldPassword;
        goState();
    }

    public void goState(){
        stateSBS.goState();
    }

    public void setStateSBS(StateSBS stateSBS) {
        this.stateSBS = stateSBS;
        goState();
    }

    public Logger getLogger() {
        return logger;
    }

    void setToFormStrStatus(String strStatus) {
        frmPassword.setTextToStatus(strStatus);
    }

    void setToFormStrLabel(String strLabel) {
        frmPassword.setTextToLabelPassword(strLabel);
    }

    CreatePasswordField getPfPassword() {
        return frmPassword.getPfPassword();
    }

    void setFocusOnPasswordField() {
        frmPassword.getPfPassword().requestFocus();
    }

    boolean isVisibleFrm() {
        return frmPassword.isFlVisibleFrm();
    }

    void setOk() {
        flOk = true;
    }

    public boolean isFlOk() {
        return flOk;
    }

    void setFlChangePassword(boolean setYesNo) {
        flChangePassword = setYesNo;
        frmPassword.getPfPassword().setText("");
    }

    boolean isChangePassword() {
        return flChangePassword;
    }

    public StateSBS getStateSBS() {
        return stateSBS;
    }
}
