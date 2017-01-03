package frm.password.sbs;

public class SBSEscape extends SBSNoWaitEnter {

    SBSEscape(StepByStep stepByStep) {
        super(stepByStep);
    }

    @Override
    public void goState() {
        getLogger().info("-> сброс ввода пароля");
        getStepByStep().setToFormStrLabel("Пароль доступа");
        getStepByStep().setFlChangePassword(false);
        getStepByStep().setStateSBS(getStepByStep().sbsInputOldPassword);
    }
}
