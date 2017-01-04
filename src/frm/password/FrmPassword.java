package frm.password;

import frm.gui.*;
import frm.password.sbs.StepByStep;
import utils.PrefParam;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class FrmPassword  extends CreateFrm implements  Runnable, InsideMessenger{
    private JLabel lPassword;
    private CreateStatusBar lStatusBar;
    private Logger logger;
    private CreateButton btnPassword;
    private CreatePasswordField pfPassword;
    private StepByStep stepByStep;

    private InsideListener insideListener;

    public FrmPassword(Logger logger) {
        super(BorderLayout.NORTH);
        this.logger = logger;
        setResizable(false);
        setTitle("Режим администратора");

        JPanel jpPasswordBox = new CreateLineBorderBox(0, 0, " Доступ к режиму ");

        JPanel jpPassword = BoxLayoutUtils.createVerticalPanel();

        JPanel jplPassword = BoxLayoutUtils.createHorizontalPanel();
        lPassword = new CreateLabel(0, 0,
                SwingConstants.LEFT, SwingConstants.LEFT, "");
        setTextToLabelPassword("Пароль доступа");
        jplPassword.add(Box.createHorizontalStrut(10));
        jplPassword.add(lPassword);
        jplPassword.add(Box.createHorizontalGlue());

        jpPassword.add(Box.createVerticalStrut(4));
        jpPassword.add(jplPassword);

        JPanel jppfPassword = BoxLayoutUtils.createHorizontalPanel();
        pfPassword = new CreatePasswordField(PrefParam.PREF_PASS, '*');
        btnPassword = new CreateButton(110,18,"Ввести пароль");
        jppfPassword.add(Box.createHorizontalStrut(10));
        jppfPassword.add(pfPassword);
        jppfPassword.add(btnPassword);
        jppfPassword.add(Box.createHorizontalStrut(10));

        jpPassword.add(Box.createVerticalStrut(4));
        jpPassword.add(jppfPassword);

        JPanel jpCheckPass = BoxLayoutUtils.createHorizontalPanel();
        JCheckBox chbPassword = new CreateCheckBox(0, 0,
                SwingConstants.RIGHT, SwingConstants.LEFT, " не показывать");
        chbPassword.setSelected(true);
        chbPassword.addActionListener(e -> {
            if (chbPassword.isSelected()) pfPassword.setEchoChar('*');
            else pfPassword.setEchoChar((char) 0);
            chbPassword.setFocusPainted(false);
        });
        CreateButton btnChangePassword = new CreateButton(110,18,"Изменить пароль");
        btnChangePassword.setStyle_Underline();
        btnChangePassword.addActionListener(e ->
                stepByStep.setStateSBS(stepByStep.sbsChangePassword));
        jpCheckPass.add(Box.createHorizontalStrut(6));
        jpCheckPass.add(chbPassword);
        jpCheckPass.add(btnChangePassword);
        jpCheckPass.add(Box.createHorizontalStrut(9));
        jpCheckPass.add(Box.createHorizontalGlue());

        jpPassword.add(jpCheckPass);
        jpPassword.add(Box.createVerticalStrut(10));

        lStatusBar = new CreateStatusBar(this);
        setTextToStatus("ожидание ввода пароля...");

        jpPasswordBox.add(jpPassword);
        getJpMain().add(jpPasswordBox);

        getJpContainerMain().add(getJpMain());

        viewModalFrm(330, 150, false);
    }

    public void setTextToLabelPassword(String strText){
        lPassword.setText(strText + ":");
    }

    public void setTextToStatus(String strText){
        lStatusBar.getJlStatus().setText(strText);
    }

    public CreatePasswordField getPfPassword() {
        return pfPassword;
    }

    @Override
    public InsideListener getListener() {
        return insideListener;
    }

    public StepByStep getStepByStep() {
        return stepByStep;
    }

    public void go() {
        setVisible(true);
        setFlVisibleFrm(true);
        insideListener = new GUIInsideListener(this);
        pfPassword.registerListener(this);
        btnPassword.registerListener(this);
        Thread threadFrm = new Thread(this);
        threadFrm.start();
    }

    @Override
    public void run() {
        goSBS();
    }

    private void goSBS() {
        stepByStep = new StepByStep(logger, this);
        stepByStep.goStart();
        while (isFlVisibleFrm() && !stepByStep.isFlOk()){
            Thread.yield();
        }
        setVisible(false);
        setFlVisibleFrm(false);
    }

    public boolean getResult() {
        return stepByStep != null && stepByStep.isFlOk();
    }
}
