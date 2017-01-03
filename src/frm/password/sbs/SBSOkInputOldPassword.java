package frm.password.sbs;

public class SBSOkInputOldPassword extends SBSNoWaitEnter {

    SBSOkInputOldPassword(StepByStep stepByStep) {
        super(stepByStep);
    }

    @Override
    public void goState() {
        if (getStepByStep().isChangePassword()){
            getLogger().info("-> смена пароля -> старый пароль введён правильно");
            getStepByStep().setToFormStrLabel("Изменение пароля -> новый пароль");
            getStepByStep().setToFormStrStatus("введите новый пароль...");
            getStepByStep().setStateSBS(getStepByStep().sbsInputNewPassword);
        }else {
            getLogger().info("-> пароль введён правильно");
            getStepByStep().setToFormStrLabel("Пароль доступа");
            getStepByStep().setToFormStrStatus("пароль введён правильно!");
            getStepByStep().setOk();
        }
    }
}
