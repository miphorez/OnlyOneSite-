package content;

import chrriis.dj.nativeswing.swtimpl.components.*;
import content.messenger.Messenger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xml.preset.TContent;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

public class Content_ONLINELIFE extends DecoratorContent {
    private static final String urlMain = "http://www.online-life.cc/";
    private static final String urlBookmarks = "http://www.online-life.cc/favorites/";
    private static final String urlLogOut = "http://www.online-life.cc/index.php?action=logout";
    private static final String urlFavoritesOut = "http://www.online-life.cc/index.php?do=favorites&amp;doaction=del";

    public Content_ONLINELIFE(Logger logger, TContent tContent, Messenger messenger) {
        super(logger, tContent, messenger);
    }

    @Override
    public void itemContent() {
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            @Override
            public void locationChanging(WebBrowserNavigationEvent e) {
                strNewResource = e.getNewResourceLocation();
                if (checkAdminMode()) {
                    webBrowser.navigate(urlBookmarks);
                    return;
                }
                if (!isContentLegal()) {
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

            @Override
            public void locationChanged(WebBrowserNavigationEvent e) {
                super.locationChanged(e);
                final String strLocation = e.getNewResourceLocation();
                logger.info("загружена ссылка: " + strLocation);
                if (strLocation.indexOf(urlBookmarks, 0) != -1) {
                    String htmlSource = webBrowser.getHTMLContent();
                    bookmarkContent = new BookmarkContent(logger, htmlSource);
                    bookmarkContent.typeList();
                }
            }
        });
    }

    @Override
    boolean isContentLegal() {
        return (strNewResource.indexOf("http://tredman.com", 0) != -1) ||
                (strNewResource.indexOf("dterod.com", 0) != -1) ||
                (strNewResource.indexOf(urlBookmarks, 0) == 0) ||
                ((strNewResource.indexOf(urlMain, 0) == 0) && (strNewResource.length() == urlMain.length())) ||
                (strNewResource.indexOf(urlLogOut, 0) != -1) ||
                (strNewResource.indexOf(urlFavoritesOut, 0) != -1) ||
                bookmarkContent.isResourseInListLegalURL(strNewResource);
    }

    class BookmarkContent {
        private Logger logger;
        private String htmlSource;
        private ArrayList<String> listLegalURL = new ArrayList<>();

        BookmarkContent(Logger logger, String htmlSource) {
            this.logger = logger;
            this.htmlSource = htmlSource;
            createListLegalURL();
        }

        private void createListLegalURL() {
            Document doc = Jsoup.parse(htmlSource);

            Elements elements = doc.getElementsByClass("custom-poster");
            for (Element e : elements) {
                Elements esInside1 = e.getElementsByTag("a");
                if (esInside1 != null) {
                    listLegalURL.add(esInside1.attr("href"));
                }
            }
        }

        void typeList() {
            if (listLegalURL.size() != 0) {
                logger.info("список легального контента:");
                for (String strItem : listLegalURL) {
                    logger.info(strItem);
                }
            } else logger.info("список легального контента пуст");
        }

        public boolean saveContent(String strForSave, String strFileName) {
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

        boolean isResourseInListLegalURL(String strNewResource) {
            for (String strItem : listLegalURL) {
                if (Objects.equals(strItem, strNewResource)) return true;
            }
            return false;
        }
    }
}

