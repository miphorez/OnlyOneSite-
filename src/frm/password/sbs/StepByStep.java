package frm.password.sbs;

import java.util.logging.Logger;

public class StepByStep {

    public StateSBS sbsInputOldPassword;        //ввод старого пароля
    public StateSBS sbsOkInputOldPassword;      //успешный ввод старого пароля
    public StateSBS sbsErrInputOldPassword;     //ошибка ввода старого пароля
    public StateSBS sbsInputNewPassword;        //ввод нового пароля
    public StateSBS sbsErrInputNewPassword;     //ошибка ввода нового пароля
    public StateSBS sbsConfirmNewPassword;      //подтверждение нового пароля
    public StateSBS sbsErrConfirmNewPassword;   //ошибка подтверждения нового пароля
    public StateSBS sbsOkNewPassword;           //успешный ввод нового пароля
    StateSBS stateSBS;

    Logger logger;

    public StepByStep(Logger log) {
        logger = log;
        logger.info("StepByStep Password: start");
        sbsInputOldPassword = new SBSInputOldPassword(this);
        sbsOkInputOldPassword = new SBSOkInputOldPassword(this);
        sbsErrInputOldPassword = new SBSErrInputOldPassword(this);
        sbsInputNewPassword = new SBSInputNewPassword(this);
        sbsErrInputNewPassword = new SBSErrInputNewPassword(this);
        sbsConfirmNewPassword = new SBSConfirmNewPassword(this);
        sbsErrConfirmNewPassword = new SBSErrConfirmNewPassword(this);
        sbsOkNewPassword = new SBSOkNewPassword(this);
    }

    public void goStart(){
        logger.info("");
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
}
