package frm.password.sbs;

import frm.gui.EStatusPasswordControl;

public class SBSConfirmNewPassword extends SBSWaitEnter {

    SBSConfirmNewPassword(StepByStep stepByStep) {
        super(stepByStep);
    }

    @Override
    public void goState() {
        setFlExitFromState(false);
        getLogger().info("-> смена пароля -> подтверждение нового пароля");
        getStepByStep().setToFormStrLabel("Изменение пароля -> подтвердите новый пароль");
        getStepByStep().getPfPassword().setText("");
        getStepByStep().setFocusOnPasswordField();
        while (!isFlExitFromState() && !getStepByStep().isVisibleFrm()) {
            Thread.yield();
        }
    }

    @Override
    public void goEnter() {
        EStatusPasswordControl result = getStepByStep().getPfPassword().
                goControlTextField(EStatusPasswordControl.GO_NEW_PASSWORD_CONFIRM);
        setFlExitFromState(true);
        switch (result) {
            case TOO_SHORT:
                getStepByStep().setToFormStrStatus("слишком короткий пароль...");
                getStepByStep().setStateSBS(getStepByStep().sbsErrInputPassword);
                break;
            case OK:
                getStepByStep().setToFormStrStatus("новый пароль введён...");
                getStepByStep().setStateSBS(getStepByStep().sbsOkNewPassword);
                break;
            default:
                getStepByStep().setToFormStrStatus("ошибка ввода пароля...");
                getStepByStep().setStateSBS(getStepByStep().sbsErrInputPassword);
        }
    }
}
