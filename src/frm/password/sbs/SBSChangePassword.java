package frm.password.sbs;

public class SBSChangePassword extends SBSNoWaitEnter {

    SBSChangePassword(StepByStep stepByStep) {
        super(stepByStep);
    }

    @Override
    public void goState() {
        getLogger().info("-> смена пароля");
        getStepByStep().setToFormStrLabel("Изменение пароля -> введите старый пароль");
        getStepByStep().setToFormStrStatus("подтверждение доступа к смене пароля...");
        getStepByStep().setFlChangePassword(true);
        getStepByStep().setStateSBS(getStepByStep().sbsInputOldPassword);
    }
}
