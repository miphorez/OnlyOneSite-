package frm.about;

import frm.gui.CreateFrm;
import frm.gui.CreateLineBorderBox;
import frm.gui.CreateTextPane;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import static utils.ConstantForAll.LS;
import static utils.UtilsForAll.getMainClass;

public class FrmAboutAndVersion extends CreateFrm{
    private JTextPane taHistory;
    private JTextPane taAbout;

    public FrmAboutAndVersion() {
        super();

        JPanel jpTAabout = new CreateLineBorderBox(0, 0, "О программе");
        CreateTextPane createTextPane_About = new CreateTextPane(false);
        jpTAabout.add(createTextPane_About, BorderLayout.CENTER);
        taAbout = createTextPane_About.getjTextPane();

        JPanel jpTAhistory = new CreateLineBorderBox(0, 0, "История версий");
        CreateTextPane createTextPane_History = new CreateTextPane(false);
        jpTAhistory.add(createTextPane_History, BorderLayout.CENTER);
        taHistory = createTextPane_History.getjTextPane();

        getJpMain().add(jpTAabout);
        getJpMain().add(Box.createVerticalStrut(5));
        getJpMain().add(jpTAhistory);

        getJpContainerMain().add(getJpMain());

        setViewItemParam();
        viewModalFrm(400, 600, true);
    }

    private void setViewItemParam() {
        String TXT_ABOUT = "/res/txt/about.txt";
        String TXT_HISTORY = "/res/txt/history.txt";
        readResourceFileToTextArea(TXT_ABOUT, taAbout);
        readResourceFileToTextArea(TXT_HISTORY, taHistory);
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
