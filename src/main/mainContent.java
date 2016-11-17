package main;

import chrriis.dj.nativeswing.swtimpl.components.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Objects;
import java.util.logging.Logger;

import static utils.ConstantForAll.*;
import static utils.UtilsForAll.*;

public class mainContent {
    static boolean flAdmin = false;

    public static JComponent createContent(Logger logger) {
        JPanel contentPane = new JPanel(new BorderLayout());

        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        webBrowserPanel.setBorder(BorderFactory.createTitledBorder("OOS"));
        final JWebBrowser webBrowser = new JWebBrowser();
//        webBrowser.setBarsVisible(true);
        webBrowser.setBarsVisible(false);
//        webBrowser.navigate(new File(getFileNameHTMLContent()).toURI().toString());
        webBrowser.navigate(urlBookmarks);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);

        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            @Override
            public void locationChanging(WebBrowserNavigationEvent e) {
                String strNewResource = e.getNewResourceLocation();
                if (!isContentLegal(strNewResource)) {
                    logger.info("запрещена ссылка: " + strNewResource);
                    e.consume();
                } else {
                    logger.info("разрешена ссылка: " + strNewResource);
                }
            }

            @Override
            public void locationChanged(WebBrowserNavigationEvent e) {
                super.locationChanged(e);
                final String newResourceLocation = e.getNewResourceLocation();
                logger.info("загружена ссылка: " + newResourceLocation);
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

    public static JComponent contentLoadAndModify(Logger logger) {
        JPanel contentPane = new JPanel(new BorderLayout());

        JPanel webBrowserPanel = new JPanel(new BorderLayout());

        logger.info("логгер запущен");

        final JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.setBarsVisible(false);
        webBrowser.navigate(urlBookmarks);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);

        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            @Override
            public void locationChanging(WebBrowserNavigationEvent e) {
                String strNewResource = e.getNewResourceLocation();
                if (!isContentLegal(strNewResource)) {
                    logger.info("запрещена ссылка: " + strNewResource);
                    e.consume();
                } else {
                    logger.info("разрешена ссылка: " + strNewResource);
                    if (isContentURL(strNewResource)) {
                        if (saveAndModifyContentInFile(logger, strNewResource))
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

    public static JComponent createLegalContent(Logger logger) {
        JPanel contentPane = new JPanel(new BorderLayout());
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        logger.info("логгер запущен");

        final JWebBrowser webBrowser = new JWebBrowser();
        webBrowserPanel.setBorder(BorderFactory.createTitledBorder(GLOBAL_CONTENT));
        webBrowser.setBarsVisible(true);
        webBrowser.setMenuBarVisible(false);
        webBrowser.navigate(urlBookmarks);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);

        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            @Override
            public void locationChanging(WebBrowserNavigationEvent e) {
                String strNewResource = e.getNewResourceLocation();
                if (checkAdminMode(strNewResource, webBrowserPanel)) {
                    webBrowser.navigate(urlBookmarks);
                    return;
                }
                if (!isContentLegal(strNewResource)) {
                    logger.info("запрещена ссылка: " + strNewResource);
                    if (!flAdmin) e.consume();
                } else {
                    logger.info("разрешена ссылка: " + strNewResource);
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

    private static boolean checkAdminMode(String strNewResource, JPanel webBrowserPanel) {
        if (Objects.equals(strNewResource, urlAdminPass)) {
            flAdmin = !flAdmin;
            if (flAdmin) {
                webBrowserPanel.setBorder(BorderFactory.createTitledBorder(GLOBAL_CONTENT + " [admin mode]"));
            } else {
                webBrowserPanel.setBorder(BorderFactory.createTitledBorder(GLOBAL_CONTENT));
            }
            return true;
        }
        return false;
    }

    private static boolean saveAndModifyContentInFile(Logger logger, String strNewResource) {
        String strContent;
        try {
            Document doc = Jsoup.connect(strNewResource).get();
            if (saveContent(logger, doc.toString(), getFileNameHTMLContent())) {
                strContent = contentModify(logger, getFileNameHTMLContent());
                if (Objects.equals(strContent, "")) {
                    logger.info("ошибка модификации контента");
                    return false;
                }
            } else {
                logger.info("ошибка записи контента в файл: " + getFileNameHTMLContent());
            }
        } catch (IOException e) {
            logger.info("ошибка загрузки контента: " + e.getMessage());
        }

        logger.info("загрузка модифицированного контента");
        return true;
    }

    private static boolean isContentURL(String strNewResource) {
        return Objects.equals(strNewResource, urlContent);
    }

    private static String contentModify(Logger logger, String fileNameHTMLContent) {
        logger.info("модификация контента");
        File input = new File(fileNameHTMLContent);
        Document doc;
        try {
            doc = Jsoup.parse(input, "cp1251", "");
            Element element = doc.getElementById("dle-content");
            if (element != null) {
                Document docNew = Jsoup.parse(element.html());

                docNew.getElementById("mc-container").remove();

                removeElementByClass(docNew, "post-title");
                removeElementByClass(docNew, "related-block");
                removeElementByTag(docNew, "noindex");
                removeElementByClass(docNew, "pull-left", "rating");
                saveContent(logger, docNew.html(), getFileNameHTMLContent());
                return docNew.html();
            }
        } catch (IOException e) {
            logger.info("ошибка парсинка контента: " + e.getMessage());
        }
        return "";
    }

    private static void removeElementByTag(Document doc, String strElement) {
        Elements elements = doc.getElementsByTag(strElement);
        elements.forEach(Node::remove);
    }

    private static void removeElementByClass(Document doc, String strElement) {
        Elements elements = doc.getElementsByClass(strElement);
        elements.forEach(Node::remove);
    }

    private static void removeElementByClass(Document doc, String strElement1, String strElement2) {
        Elements elements = doc.getElementsByClass(strElement1);
        for (Element e : elements) {
            Elements esInside1 = e.getElementsByClass(strElement2);
            if (esInside1 != null) {
                esInside1.remove();
            }
        }
    }

    private static boolean isContentLegal(String strResLocation) {
        return (strResLocation.indexOf("http://tredman.com", 0) != -1) ||
                (strResLocation.indexOf("dterod.com", 0) != -1) ||
                (strResLocation.indexOf(FILE_HTML_CONTENT, 0) != -1) ||
                (strResLocation.indexOf(urlBookmarks, 0) == 0) ||
                ((strResLocation.indexOf(urlMain, 0) == 0) && (strResLocation.length() == urlMain.length())) ||
                (strResLocation.indexOf(urlLogOut, 0) != -1) ||
                (strResLocation.indexOf(urlFavoritesOut, 0) != -1) ||
                (strResLocation.indexOf(urlContent, 0) != -1);
    }

    public static boolean saveContent(Logger logger, String strForSave, String strFileName) {
        try {
            OutputStream out = new FileOutputStream(strFileName);
            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(out, "cp1251"));
            fileWriter.write(strForSave, 0, strForSave.length());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            logger.info("ошибка записи контента в файл: " + e.getMessage());
            return false;
        }
        return true;
    }

}
