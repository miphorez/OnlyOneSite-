package content;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;
import content.messenger.Messenger;
import xml.preset.TContent;

import javax.swing.*;
import java.util.logging.Logger;

public class Content_HTML extends DecoratorContent {
    public Content_HTML(Logger logger, TContent tContent, Messenger messenger) {
        super(logger, tContent, messenger);
    }

    @Override
    public void itemContent() {

        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            @Override
            public void locationChanging(WebBrowserNavigationEvent e) {
                strNewResource = e.getNewResourceLocation();
                if (checkAdminMode()) {
                    webBrowser.navigate(getTContent().getLink());
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
    }

    @Override
    boolean isContentLegal() {
        return (strNewResource.indexOf(getTContent().getLink(), 0) != -1) &&
                (strNewResource.length() == getTContent().getLink().trim().length()) ;
    }
}
