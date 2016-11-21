package content;

import chrriis.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.components.*;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

import static utils.ConstantForAll.*;

public class TabsContent {
    private Logger logger;
    private JWebBrowser webBrowser;

    public TabsContent(Logger logger) {
        this.logger = logger;
    }

    public JComponent createListContent() {
        JPanel contentPane = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        webBrowser = new JWebBrowser();
        webBrowser.setBarsVisible(false);
        webBrowser.setLocationBarVisible(true);
        webBrowser.setButtonBarVisible(true);
//        webBrowser.setMenuBarVisible(false);
        webBrowser.setStatusBarVisible(true);
//        webBrowser.setHTMLContent(
//                "<html>" + LS +
//                        "  <body>" + LS +
//                        "    <a href=\"http://www.google.com\">http://www.google.com</a>: normal link.<br/>" + LS +
//                        "    <a href=\"http://www.google.com\" target=\"_blank\">http://www.google.com</a>: link that normally opens in new window.<br/>" + LS +
//                        "  </body>" + LS +
//                        "</html>");
        webBrowser.navigate(WebServer.getDefaultWebServer().getClassPathResourceURL(getClass().getName(), RES_PAGE_CONTENT));
        addWebBrowserListener(tabbedPane, webBrowser);

        tabbedPane.addTab("Содержание", webBrowser);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        return contentPane;
    }

    private void addWebBrowserListener(final JTabbedPane tabbedPane, final JWebBrowser webBrowser) {
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            @Override
            public void titleChanged(WebBrowserEvent e) {
                for(int i=0; i<tabbedPane.getTabCount(); i++) {
                    if(tabbedPane.getComponentAt(i) == webBrowser) {
                        if(i == 0) {
                            return;
                        }
                        tabbedPane.setTitleAt(i, webBrowser.getPageTitle());
                        break;
                    }
                }
            }
            @Override
            public void windowWillOpen(WebBrowserWindowWillOpenEvent e) {
                JWebBrowser newWebBrowser = new JWebBrowser();
                addWebBrowserListener(tabbedPane, newWebBrowser);
                tabbedPane.addTab("New Tab", newWebBrowser);
                e.setNewWebBrowser(newWebBrowser);
            }
            @Override
            public void windowOpening(WebBrowserWindowOpeningEvent e) {
                e.getWebBrowser().setMenuBarVisible(false);
            }
        });
    }
}
