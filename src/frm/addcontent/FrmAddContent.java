package frm.addcontent;

import frm.gui.*;
import xml.preset.ETContent;
import xml.preset.TContent;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class FrmAddContent extends CreateFrm implements  Runnable, InsideMessenger {
    private CreateComboBoxTypeContent cbType;
    private Logger logger;
    private CreateTextField tfLink;
    private CreateTextField tfName;
    private TContent resultContent;
    private CreateButton btnAddContent;

    private InsideListener insideListener;
    private boolean flOk;

    public FrmAddContent(Logger logger) {
        super(BorderLayout.NORTH);
        this.logger = logger;
        setResizable(false);
        setTitle("Добавление содержания");

        JPanel jpContentBox = new CreateLineBorderBox(0, 0, " Параметры ресурса ");
        JPanel jpContent = BoxLayoutUtils.createVerticalPanel();
//-------------
        JPanel jplLink = BoxLayoutUtils.createHorizontalPanel();
        JLabel lLink = new CreateLabel(0, 0,
                SwingConstants.LEFT, SwingConstants.LEFT, "Ссылка на ресурс:");
        jplLink.add(Box.createHorizontalStrut(10));
        jplLink.add(lLink);

        jpContent.add(Box.createVerticalStrut(4));
        jpContent.add(jplLink);

        JPanel jptfLink = BoxLayoutUtils.createHorizontalPanel();
        tfLink = new CreateTextField();
        jptfLink.add(Box.createHorizontalStrut(10));
        jptfLink.add(tfLink);
        jptfLink.add(Box.createHorizontalStrut(10));

        jpContent.add(Box.createVerticalStrut(4));
        jpContent.add(jptfLink);
//-------------
        JPanel jplName = BoxLayoutUtils.createHorizontalPanel();
        JLabel lName = new CreateLabel(0, 0,
                SwingConstants.LEFT, SwingConstants.LEFT, "Наименование ресурса:");
        jplName.add(Box.createHorizontalStrut(10));
        jplName.add(lName);

        jpContent.add(Box.createVerticalStrut(4));
        jpContent.add(jplName);

        JPanel jptfName = BoxLayoutUtils.createHorizontalPanel();
        tfName = new CreateTextField();
        jptfName.add(Box.createHorizontalStrut(10));
        jptfName.add(tfName);
        jptfName.add(Box.createHorizontalStrut(10));

        jpContent.add(Box.createVerticalStrut(4));
        jpContent.add(jptfName);
//-------------
        JPanel jplType = BoxLayoutUtils.createHorizontalPanel();
        JLabel lType = new CreateLabel(0, 0,
                SwingConstants.LEFT, SwingConstants.LEFT, "Тип ресурса:");
        jplType.add(Box.createHorizontalStrut(10));
        jplType.add(lType);

        jpContent.add(Box.createVerticalStrut(4));
        jpContent.add(jplType);

        JPanel jpcbType = BoxLayoutUtils.createHorizontalPanel();
        cbType = new CreateComboBoxTypeContent(0,0);
        jpcbType.add(Box.createHorizontalStrut(10));
        jpcbType.add(cbType);
        jpcbType.add(Box.createHorizontalStrut(10));

        jpContent.add(Box.createVerticalStrut(4));
        jpContent.add(jpcbType);
//-------------

        JPanel jpbtnAdd = BoxLayoutUtils.createHorizontalPanel();
        btnAddContent = new CreateButton(70,18,"Добавить");
        btnAddContent.addActionListener(e ->
                addContentToXML());
        jpbtnAdd.add(Box.createHorizontalGlue());
        jpbtnAdd.add(btnAddContent);
        jpbtnAdd.add(Box.createHorizontalStrut(10));

        jpContent.add(Box.createVerticalStrut(10));
        jpContent.add(jpbtnAdd);
//-------------

        jpContent.add(Box.createVerticalStrut(4));
        jpContentBox.add(jpContent);
        getJpMain().add(jpContentBox);

        getJpContainerMain().add(getJpMain());

        viewModalFrm(330, 220, true);
    }

    private void addContentToXML() {
        flOk = true;
        resultContent = new TContent(
                tfLink.getText().trim(),
                tfName.getText().trim(),
                ((ETContent)cbType.getSelectedItem()).name());
    }

    public TContent getResultContent() {
        return flOk ? resultContent : null;
    }

    public void go(String strNewLink) {
        setTitle("Добавить содержание");
        btnAddContent.setText("Добавить");
        setVisible(true);
        setFlVisibleFrm(true);
        insideListener = new GUIListenerAddContent(this);
        tfLink.registerListener(this);
        tfLink.setText(strNewLink);
        tfLink.setEnabled(true);
        cbType.setEnabled(true);
        Thread threadFrm = new Thread(this);
        threadFrm.start();
    }

    public void goUpdate(TContent tContent) {
        setTitle("Редактировать содержание");
        btnAddContent.setText("Изменить");
        setVisible(true);
        setFlVisibleFrm(true);
        insideListener = new GUIListenerAddContent(this);
        tfLink.setText(tContent.getLink());
        if (tContent.isModeDel())
            tfLink.setEnabled(true); else tfLink.setEnabled(false);
        tfName.setText(tContent.getName());
        cbType.setSelectedType(tContent.getType());
        if (tContent.isModeDel())
            cbType.setEnabled(true); else cbType.setEnabled(false);
        Thread threadFrm = new Thread(this);
        threadFrm.start();
    }

    @Override
    public InsideListener getListener() {
        return insideListener;
    }

    @Override
    public void run() {
        while (isFlVisibleFrm() && !flOk){
            Thread.yield();
        }
        setVisible(false);
        setFlVisibleFrm(false);
    }

}
