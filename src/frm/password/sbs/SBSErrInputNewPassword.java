package frm.password.sbs;

import java.util.logging.Logger;

public class SBSErrInputNewPassword implements StateSBS {
    StepByStep stepByStep;
    Logger logger;

    public SBSErrInputNewPassword(StepByStep stepByStep) {
        this.stepByStep = stepByStep;
        logger = this.stepByStep.getLogger();
    }

    @Override
    public void goState() {
        logger.info("->");
        stepByStep.setStateSBS(stepByStep.sbsErrInputNewPassword);
        stepByStep.setStateSBS(stepByStep.sbsOkInputOldPassword);
    }
}
