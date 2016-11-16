package main;

import chrriis.dj.nativeswing.swtimpl.components.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.logging.Logger;

import static utils.ConstantForAll.*;
import static utils.UtilsForAll.*;

public class mainContent {


    public static JComponent createContent(Logger logger) {
        JPanel contentPane = new JPanel(new BorderLayout());

        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        webBrowserPanel.setBorder(BorderFactory.createTitledBorder("OOS"));
        final JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.setBarsVisible(true);
//        webBrowser.navigate(new File(getFileNameHTMLContent()).toURI().toString());
        webBrowser.navigate(urlContent);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);

        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            @Override
            public void locationChanging(WebBrowserNavigationEvent e) {
                final String newResourceLocation = e.getNewResourceLocation();
                String strResLocation = newResourceLocation.toString();
                if (
                        (strResLocation.indexOf("http://tredman.com", 0) != -1) ||
                                (strResLocation.indexOf("dterod.com", 0) != -1) ||
                                (strResLocation.indexOf(urlBookmarks, 0) != -1) ||
                                (strResLocation.indexOf(FILE_HTML_CONTENT, 0) != -1) ||
                                (strResLocation.indexOf(urlContent, 0) != -1)
                        ) {
                    logger.info("разрешена ссылка: " + strResLocation);
                    return;
                } else {
                    logger.info("запрещена ссылка: " + strResLocation);
                    e.consume();
                }
            }

            @Override
            public void locationChanged(WebBrowserNavigationEvent e) {
                super.locationChanged(e);
                final String newResourceLocation = e.getNewResourceLocation();
                logger.info("загружена ссылка: " + newResourceLocation);
                String strResLocation = newResourceLocation.toString();
                if (newResourceLocation.indexOf("http://tredman.com", 0) != -1) {
                    String htmlSource = webBrowser.getHTMLContent();
                    if (saveContent(logger, htmlSource, getFileNameHTMLContent())) {
                        logger.info("контент записан в файл: " + getFileNameHTMLContent());
                        webBrowser.navigate(new File(getFileNameHTMLContent()).toURI().toString());
                    }
                }
            }

            @Override
            public void windowWillOpen(WebBrowserWindowWillOpenEvent e) {
                e.getNewWebBrowser().addWebBrowserListener(new WebBrowserAdapter() {
                    @Override
                    public void locationChanging(WebBrowserNavigationEvent e) {
                        final JWebBrowser webBrowser = e.getWebBrowser();
                        webBrowser.removeWebBrowserListener(this);
                        e.consume();
                        SwingUtilities.invokeLater(() -> webBrowser.getWebBrowserWindow().dispose());
                    }
                });
            }
        });

        contentPane.add(webBrowserPanel, BorderLayout.CENTER);
        return contentPane;
    }

    private static boolean saveContent(Logger logger, String strForSave, String strFileName) {
        try {
            OutputStream out = new FileOutputStream(strFileName);
            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(out, "cp1251"));
//            File file = new File(strFileName);
//            FileWriter fileWriter = new FileWriter(file, false);
//
//            writeContentToHTMLFile(fileWriter, getStringInCp1251(strForSave));
            fileWriter.write(strForSave, 0, strForSave.length());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            logger.info("ошибка записи контента в файл: " + e.getMessage());
            return false;
        }
        return true;
    }

    private static void writeContentToHTMLFile(FileWriter fileWriter, String strForSave) throws IOException {
        fileWriter.write(strForSave);
    }
}
