package frm.password.sbs;

import frm.gui.EStatusPasswordControl;

public class SBSInputOldPassword extends SBSWaitEnter {

    SBSInputOldPassword(StepByStep stepByStep) {
        super(stepByStep);
    }

    @Override
    public void goState() {
        setFlExitFromState(false);
        if (getStepByStep().isChangePassword())
            getLogger().info("-> смена пароля -> ввести старый пароль");
        else
            getLogger().info("-> ввести пароль доступа");
        getStepByStep().getPfPassword().setText("");
        getStepByStep().setFocusOnPasswordField();
        while (!isFlExitFromState() && !getStepByStep().isVisibleFrm()) {
            Thread.yield();
        }
    }

    @Override
    public void goEnter() {
        EStatusPasswordControl result = getStepByStep().getPfPassword().
                goControlTextField(EStatusPasswordControl.GO_CONTROL);
        setFlExitFromState(true);
        switch (result) {
            case TOO_SHORT:
                getStepByStep().setToFormStrStatus("слишком короткий пароль...");
                getStepByStep().setStateSBS(getStepByStep().sbsErrInputPassword);
                break;
            case WRONG:
                getStepByStep().setToFormStrStatus("пароль введён неправильно...");
                getStepByStep().setStateSBS(getStepByStep().sbsErrInputPassword);
                break;
            case OK:
                getStepByStep().setToFormStrStatus("пароль введён правильно...");
                getStepByStep().setStateSBS(getStepByStep().sbsOkInputOldPassword);
                break;
            default:
                getStepByStep().setToFormStrStatus("ошибка ввода пароля...");
                getStepByStep().setStateSBS(getStepByStep().sbsErrInputPassword);
        }
    }
}
