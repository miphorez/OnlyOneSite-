package frm.password.sbs;

public class SBSErrInputPassword extends SBSNoWaitEnter {

    SBSErrInputPassword(StepByStep stepByStep) {
        super(stepByStep);
    }

    @Override
    public void goState() {
        getLogger().info("-> ошибка ввода пароля");
        getStepByStep().setToFormStrLabel("Пароль доступа");
        getStepByStep().setFlChangePassword(false);
        getStepByStep().setStateSBS(getStepByStep().sbsInputOldPassword);
    }
}
