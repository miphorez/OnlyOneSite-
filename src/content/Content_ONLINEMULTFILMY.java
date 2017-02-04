package content;

import chrriis.dj.nativeswing.swtimpl.components.*;
import content.messenger.Messenger;
import xml.preset.TContent;

import javax.swing.*;
import java.util.logging.Logger;

public class Content_ONLINEMULTFILMY extends DecoratorContent {
    private static final String urlMain = "http://onlinemultfilmy.ru/";
    private static final String urlYoutube = "www.youtube.com";
    private static final String urlYoutubeWatch = "https://www.youtube.com";

    public Content_ONLINEMULTFILMY(Logger logger, TContent tContent, Messenger messenger) {
        super(logger, tContent, messenger);
    }

    @Override
    public void itemContent() {
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            @Override
            public void locationChanging(WebBrowserNavigationEvent e) {
                strNewResource = e.getNewResourceLocation();
                if (isAdminMode() && !utils.ConstantForAll.DEBUG) return;
                if (!isContentLegal()) {
                    logger.info("запрещена ссылка: " + strNewResource);
                    e.consume();
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
            }

            @Override
            public void loadingProgressChanged(WebBrowserEvent e) {
                int progress = webBrowser.getLoadingProgress();
                if (progress == 100) {
                    logger.info("загружено 100% страницы");
//                    webBrowser.executeJavascript(jsCommand);

                }
            }
        });
    }

    @Override
    boolean isContentLegal() {
        if (strNewResource.indexOf(urlMain, 0) == 0) return true;
        if (strNewResource.indexOf(urlYoutubeWatch, 0) == 0) return true;
        if (strNewResource.contains(urlYoutube)) {
            int pos = strNewResource.indexOf("?list=");
            if (pos != -1){
                strNewResource = strNewResource.substring(0,pos);
                pos = strNewResource.indexOf("http:");
                if (pos != -1){
                    strNewResource = strNewResource.replaceAll("http:","https:");
                    pos = strNewResource.indexOf("embed/");
                    if (pos != -1){
                        strNewResource = strNewResource.replaceAll("embed/","watch?v=");
                    }
                    if (isFlMousePressed()) {
                        setFlMousePressed(false);
                        webBrowser.navigate(strNewResource);
                    }
                    return false;
                }
            }
        }
//    https://www.youtube.com/embed/K-yTrFDHpbw?list=PLXnIohISHNIvsnUNe8RwvhksUSFzdQsiT
//    https://www.youtube.com/watch?list=PLXnIohISHNIvsnUNe8RwvhksUSFzdQsiT&v=K-yTrFDHpbw
//    https://www.youtube.com/watch?v=K-yTrFDHpbw
        return false;
    }

}