package frm.about;

import frm.gui.CreateFrm;
import frm.gui.CreateLineBorderBox;
import frm.gui.CreateTextPane;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import static utils.ConstantForAll.LS;
import static utils.UtilsForAll.getMainClass;

public class FrmAboutAndVersion extends CreateFrm {
    private JTextPane taHistory;
    private JTextPane taAbout;
    private JTextPane taCopying;

    public FrmAboutAndVersion() {
        super(BorderLayout.CENTER);

        JPanel jpTACopying = new CreateLineBorderBox(0, 0, "Лицензия программы");
        CreateTextPane createTextPane_Copying = new CreateTextPane(false);
        jpTACopying.add(createTextPane_Copying, BorderLayout.CENTER);
        taCopying = createTextPane_Copying.getjTextPane();
        taCopying.addHyperlinkListener(this::gotoLinkByDefaultBrowser);

        JPanel jpTAabout = new CreateLineBorderBox(0, 0, "О программе");
        CreateTextPane createTextPane_About = new CreateTextPane(false);
        jpTAabout.add(createTextPane_About, BorderLayout.CENTER);
        taAbout = createTextPane_About.getjTextPane();
        taAbout.addHyperlinkListener(this::gotoLinkByDefaultBrowser);

        JPanel jpTAhistory = new CreateLineBorderBox(0, 0, "История версий");
        CreateTextPane createTextPane_History = new CreateTextPane(false);
        jpTAhistory.add(createTextPane_History, BorderLayout.CENTER);
        taHistory = createTextPane_History.getjTextPane();
        taHistory.addHyperlinkListener(this::gotoLinkByDefaultBrowser);

        getJpMain().add(jpTACopying);
        getJpMain().add(Box.createVerticalStrut(5));
        getJpMain().add(jpTAabout);
        getJpMain().add(Box.createVerticalStrut(5));
        getJpMain().add(jpTAhistory);

        getJpContainerMain().add(getJpMain());

        setViewItemParam();
        viewModalFrm(400, 600, true);
    }

    private void gotoLinkByDefaultBrowser(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if (Desktop.isDesktopSupported()) {
                try {
                    String strLink = e.getDescription();
                    Desktop.getDesktop().browse(new URI(strLink));
                    //TODO сделать загрузку страниц в самой программе
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void setViewItemParam() {
        String TXT_ABOUT = "/res/txt/about.txt";
        String TXT_HISTORY = "/res/txt/history.txt";
        String TXT_COPYING = "/res/txt/copying.txt";
        readResourceFileToTextArea(TXT_ABOUT, taAbout);
        readResourceFileToTextArea(TXT_HISTORY, taHistory);
        readResourceFileToTextArea(TXT_COPYING, taCopying);
        //TODO добавить эмблему лицензии в программу
        getJpMain().revalidate();
    }

    private static void readResourceFileToTextArea(String strFileNameInResource, JTextPane jTextPane) {
        InputStream inputStream = getMainClass().getResourceAsStream(strFileNameInResource);
        Reader reader = null;
        try {
            reader = new InputStreamReader(inputStream, "Cp1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = null;
        if (reader != null) {
            bufferedReader = new BufferedReader(reader);
        }
        String oneLine;
        jTextPane.setText("");
        String strArea = "";
        try {
            if (bufferedReader != null) {
                while ((oneLine = bufferedReader.readLine()) != null) {
                    strArea += oneLine + LS;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        jTextPane.setText(strArea);
        jTextPane.setCaretPosition(0);
    }
}
