package frm.password.sbs;

import frm.gui.EStatusPasswordControl;

import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class SBSInputNewPassword extends SBSWaitEnter {

    SBSInputNewPassword(StepByStep stepByStep) {
        super(stepByStep);
    }

    @Override
    public void goState() {
        setFlExitFromState(false);
        getLogger().info("-> смена пароля -> ввести новый пароль");
        getStepByStep().setFocusOnPasswordField();
        getStepByStep().getPfPassword().setText("");
        while (!isFlExitFromState() && !getStepByStep().isVisibleFrm()) {
            Thread.yield();
        }
    }

    @Override
    public void goEnter() {
        EStatusPasswordControl result = getStepByStep().getPfPassword().
                goControlTextField(EStatusPasswordControl.GO_NEW_PASSWORD);
        setFlExitFromState(true);
        switch (result) {
            case TOO_SHORT:
                getStepByStep().setToFormStrStatus("слишком короткий пароль...");
                getStepByStep().setStateSBS(getStepByStep().sbsErrInputPassword);
                break;
            case OK:
                getStepByStep().setToFormStrStatus("подтверждение нового пароля...");
                getStepByStep().setStateSBS(getStepByStep().sbsConfirmNewPassword);
                break;
            default:
                getStepByStep().setToFormStrStatus("ошибка ввода пароля...");
                getStepByStep().setStateSBS(getStepByStep().sbsErrInputPassword);
        }
    }
}
