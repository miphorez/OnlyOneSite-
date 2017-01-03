package frm.password.sbs;

import java.util.logging.Logger;

public abstract class SBSNoWaitEnter implements StateSBS {
    private StepByStep stepByStep;
    private Logger logger;

    SBSNoWaitEnter(StepByStep stepByStep) {
        this.stepByStep = stepByStep;
        logger = this.stepByStep.getLogger();
    }

    @Override
    public void goEnter() {

    }

    @Override
    public void goReset() {
    }

    public StepByStep getStepByStep() {
        return stepByStep;
    }

    public Logger getLogger() {
        return logger;
    }
}
