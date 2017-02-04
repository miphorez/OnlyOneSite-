package frm.about;

import content.messenger.ArgForMessenger;
import content.messenger.Messenger;
import content.messenger.MessengerChangeContent;
import frm.gui.CreateFrm;
import frm.gui.CreateLineBorderBox;
import frm.gui.CreateTextPane;
import xml.preset.ETContent;
import xml.preset.TContent;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.*;

import static utils.ConstantForAll.LS;
import static utils.UtilsForAll.getFileNameResourceImgInTemp;
import static utils.UtilsForAll.getMainClass;

public class FrmAboutAndVersion extends CreateFrm {
    private JTextPane taHistory;
    private JTextPane taAbout;
    private JTextPane taCopying;
    private static MessengerChangeContent messengerContent = new MessengerChangeContent();

    private static FrmAboutAndVersion ourInstance;

    public static FrmAboutAndVersion getInstance(Messenger messenger) {
        if (ourInstance == null) {
            ourInstance = new FrmAboutAndVersion();
        }
        messengerContent.addObserver(messenger.getListener());
        return ourInstance;
    }

    private FrmAboutAndVersion() {
        super(BorderLayout.CENTER);

        JPanel jpTACopying = new CreateLineBorderBox(0, 0, "Лицензия программы");
        CreateTextPane createTextPane_Copying = new CreateTextPane(false);
        jpTACopying.add(createTextPane_Copying, BorderLayout.CENTER);
        taCopying = createTextPane_Copying.getjTextPane();
        taCopying.addHyperlinkListener(this::gotoLink);

        JPanel jpTAabout = new CreateLineBorderBox(0, 0, "О программе");
        CreateTextPane createTextPane_About = new CreateTextPane(false);
        jpTAabout.add(createTextPane_About, BorderLayout.CENTER);
        taAbout = createTextPane_About.getjTextPane();
        taAbout.addHyperlinkListener(this::gotoLink);

        JPanel jpTAhistory = new CreateLineBorderBox(0, 0, "История версий");
        CreateTextPane createTextPane_History = new CreateTextPane(false);
        jpTAhistory.add(createTextPane_History, BorderLayout.CENTER);
        taHistory = createTextPane_History.getjTextPane();
        taHistory.addHyperlinkListener(this::gotoLink);

        getJpMain().add(jpTACopying);
        getJpMain().add(Box.createVerticalStrut(5));
        getJpMain().add(jpTAabout);
        getJpMain().add(Box.createVerticalStrut(5));
        getJpMain().add(jpTAhistory);

        getJpContainerMain().add(getJpMain());

        setViewItemParam();
        viewModalFrm(400, 600, true);
    }

    private void gotoLink(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if (Desktop.isDesktopSupported()) {
                    String strLink = e.getDescription();
                    ArgForMessenger arg = new ArgForMessenger(
                            Integer.toString(TContent.getRandomId()),
                            strLink,
                            strLink,
                            ETContent.LINK_HTML.name(),
                            "1",
                            false
                    );
                    messengerContent.putNewContent(arg);
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
                    if ((oneLine.contains(".png"))||
                        (oneLine.contains(".jpg"))||
                        (oneLine.contains(".gif")))
                    {
                        oneLine = "file:\\"+getFileNameResourceImgInTemp(oneLine.trim());
                    }
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
