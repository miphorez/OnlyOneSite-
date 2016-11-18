package content;

import chrriis.dj.nativeswing.swtimpl.components.*;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.logging.Logger;

import static utils.ConstantForAll.*;

public class LegalContent {
    private boolean flAdmin = false;
    private Logger logger;
    private JWebBrowser webBrowser;
    private JPanel webBrowserPanel;
    private String strNewResource;

    public LegalContent(Logger logger) {
        this.logger = logger;
    }

    public JComponent createLegalContent() {
        JPanel contentPane = new JPanel(new BorderLayout());
        webBrowserPanel = new JPanel(new BorderLayout());
        logger.info("логгер запущен");

        webBrowser = new JWebBrowser();
        webBrowserPanel.setBorder(BorderFactory.createTitledBorder(GLOBAL_CONTENT));
        webBrowser.setBarsVisible(true);
        webBrowser.setMenuBarVisible(false);
        webBrowser.navigate(urlBookmarks);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);

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
        });

        contentPane.add(webBrowserPanel, BorderLayout.CENTER);
        return contentPane;
    }

    private boolean checkAdminMode() {
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

    private boolean isContentLegal() {
         return (strNewResource.indexOf("http://tredman.com", 0) != -1) ||
                (strNewResource.indexOf("dterod.com", 0) != -1) ||
                (strNewResource.indexOf(FILE_HTML_CONTENT, 0) != -1) ||
                (strNewResource.indexOf(urlBookmarks, 0) == 0) ||
               ((strNewResource.indexOf(urlMain, 0) == 0) && (strNewResource.length() == urlMain.length())) ||
                (strNewResource.indexOf(urlLogOut, 0) != -1) ||
                (strNewResource.indexOf(urlFavoritesOut, 0) != -1) ||
                (strNewResource.indexOf(urlContent, 0) != -1);
    }

}
