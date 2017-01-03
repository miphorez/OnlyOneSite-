package frm.password.sbs;

import java.util.prefs.Preferences;

import static utils.UtilsForAll.getMD5String;

public class SBSOkNewPassword extends SBSNoWaitEnter {

    SBSOkNewPassword(StepByStep stepByStep) {
        super(stepByStep);
    }

    @Override
    public void goState() {
        getLogger().info("-> смена пароля -> пароль успешно изменен");
        getStepByStep().setToFormStrLabel("Пароль доступа");
        getStepByStep().setToFormStrStatus("пароль успешно изменён!");
        saveNewPassword();
        getStepByStep().setFlChangePassword(false);
        getStepByStep().setStateSBS(getStepByStep().sbsInputOldPassword);
    }

    private void saveNewPassword() {
        String strNewPassword = getMD5String(String.valueOf(getStepByStep().getPfPassword().getPassword()));
        Preferences.userRoot().put(getStepByStep().getPfPassword().getKeyPref(), strNewPassword);
    }

}
