package frm.password;

import frm.gui.*;
import utils.PrefParam;

import javax.swing.*;

public class FrmPassword  extends CreateFrm{
    private JLabel lPassword;
    private CreateStatusBar lStatusBar;

    public FrmPassword() {
        super();
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
        CreatePasswordField pfPassword = new CreatePasswordField(
                PrefParam.PREF_Password, PrefParam.PRESET_Password,
                '*', lPassword);
        JButton btnPassword = new CreateButton(110,18,"Ввести пароль");
        btnPassword.addActionListener(e -> pfPassword.goControlTextField());
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
        JButton btnChangePassword = new CreateButton(110,18,"Изменить пароль");
        btnChangePassword.addActionListener(e -> pfPassword.goControlTextField());
        jpCheckPass.add(Box.createHorizontalStrut(6));
        jpCheckPass.add(chbPassword);
        jpCheckPass.add(btnChangePassword);
        jpCheckPass.add(Box.createHorizontalStrut(9));
        jpCheckPass.add(Box.createHorizontalGlue());

        jpPassword.add(jpCheckPass);

        lStatusBar = new CreateStatusBar(this);
        setTextToStatus("ожидание ввода пароля...");

        jpPassword.add(Box.createVerticalGlue());
        jpPasswordBox.add(jpPassword);
        getJpMain().add(jpPasswordBox);

        getJpContainerMain().add(getJpMain());

        viewModalFrm(330, 150);
        goSBS();
    }

    public void setTextToLabelPassword(String strText){
        lPassword.setText(strText + ":");
    }

    public void setTextToStatus(String strText){
        lStatusBar.getJlStatus().setText(strText);
    }

    private void goSBS() {
        while (isVisible()){
            Thread.yield();
        }
    }
}
