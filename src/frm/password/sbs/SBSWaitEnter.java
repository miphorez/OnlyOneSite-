package frm.password.sbs;

import frm.gui.EStatusPasswordControl;

import java.awt.event.ActionListener;
import java.util.logging.Logger;

public abstract class SBSWaitEnter implements StateSBS {
    private StepByStep stepByStep;
    private Logger logger;
    private boolean flExitFromState;

    SBSWaitEnter(StepByStep stepByStep) {
        this.stepByStep = stepByStep;
        logger = this.stepByStep.getLogger();
    }

    @Override
    public void goReset() {
        stepByStep.setToFormStrStatus("сброс ввода пароля...");
        stepByStep.setStateSBS(stepByStep.sbsEscape);
    }

    public boolean isFlExitFromState() {
        return flExitFromState;
    }

    public void setFlExitFromState(boolean flExitFromState) {
        this.flExitFromState = flExitFromState;
    }

    public StepByStep getStepByStep() {
        return stepByStep;
    }

    public Logger getLogger() {
        return logger;
    }
}
