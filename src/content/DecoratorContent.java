package content;

import chrriis.dj.nativeswing.swtimpl.components.*;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.logging.Logger;

import static utils.ConstantForAll.*;

public class DecoratorContent {
    private static boolean flAdmin = false;
    private static Logger logger;
    private static JWebBrowser webBrowser;
    private static JPanel webBrowserPanel;
    private static String strNewResource;
    private static BookmarkContent bookmarkContent;

    public DecoratorContent(Logger logger) {
        DecoratorContent.logger = logger;
    }

    public static JComponent createContent() {
        logger.info("логгер запущен");
        JPanel contentPane = new JPanel(new BorderLayout());
        webBrowserPanel = new JPanel(new BorderLayout());
        webBrowserPanel.setBorder(BorderFactory.createTitledBorder(GLOBAL_CONTENT));

        webBrowser = new JWebBrowser() {
            @Override
            protected WebBrowserDecorator createWebBrowserDecorator(Component renderingComponent) {
                return createCustomWebBrowserDecorator(this, renderingComponent);
            }
        };
        webBrowser.setBarsVisible(false);
        webBrowser.setMenuBarVisible(false);
        webBrowser.setLocationBarVisible(false);
        webBrowser.setStatusBarVisible(true);
        webBrowser.setButtonBarVisible(true);

        webBrowser.navigate(urlBookmarks);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        contentPane.add(webBrowserPanel, BorderLayout.CENTER);

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
        return contentPane;
    }

    private static boolean checkAdminMode() {
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

    private static boolean isContentLegal() {
        return (strNewResource.indexOf("http://tredman.com", 0) != -1) ||
                (strNewResource.indexOf("dterod.com", 0) != -1) ||
                (strNewResource.indexOf(FILE_HTML_CONTENT, 0) != -1) ||
                (strNewResource.indexOf(urlBookmarks, 0) == 0) ||
                ((strNewResource.indexOf(urlMain, 0) == 0) && (strNewResource.length() == urlMain.length())) ||
                (strNewResource.indexOf(urlLogOut, 0) != -1) ||
                (strNewResource.indexOf(urlFavoritesOut, 0) != -1) ||
                bookmarkContent.isResourseInListLegalURL(strNewResource);
    }

    private static WebBrowserDecorator createCustomWebBrowserDecorator(JWebBrowser webBrowser, Component renderingComponent) {
        return new DefaultWebBrowserDecorator(webBrowser, renderingComponent) {
            @Override
            protected void addMenuBarComponents(WebBrowserMenuBar menuBar) {
                super.addMenuBarComponents(menuBar);
                JMenu myMenu = new JMenu("Содержание");
                myMenu.add(new JMenuItem("Показать список"));
                myMenu.add(new JMenuItem("Добавить ссылку"));
                myMenu.add(new JMenuItem("Удалить ссылку"));
                menuBar.add(myMenu);
            }
            @Override
            protected void addButtonBarComponents(WebBrowserButtonBar buttonBar) {
                buttonBar.add(buttonBar.getBackButton());
                buttonBar.add(buttonBar.getForwardButton());
                buttonBar.add(buttonBar.getReloadButton());
                buttonBar.add(buttonBar.getStopButton());
                final JButton btnContent = new JButton("[Содержание]");
                btnContent.addActionListener(e -> JOptionPane.showMessageDialog(btnContent, "[Содержание]"));
                buttonBar.add(btnContent);
                final JButton btnAddContent = new JButton("[Добавить]");
                btnAddContent.addActionListener(e -> JOptionPane.showMessageDialog(btnAddContent, "[Добавить]"));
                buttonBar.add(btnAddContent);
                final JButton btnDelContent = new JButton("[Удалить]");
                btnDelContent.addActionListener(e -> JOptionPane.showMessageDialog(btnDelContent, "[Удалить]"));
                buttonBar.add(btnDelContent);
            }
        };
    }
}
