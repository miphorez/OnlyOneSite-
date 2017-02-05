package content;

import chrriis.dj.nativeswing.swtimpl.components.*;
import content.messenger.Messenger;
import xml.preset.TContent;

import javax.swing.*;
import java.util.logging.Logger;

public class Content_ONLINEMULTFILMY extends DecoratorContent {
    private static final String urlMain = "http://onlinemultfilmy.ru/";
    private static final String urlYoutube = "www.youtube.com";

    private String strLegalResource;

    public Content_ONLINEMULTFILMY(Logger logger, TContent tContent, Messenger messenger) {
        super(logger, tContent, messenger);
    }

    @Override
    public void itemContent() {
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            @Override
            public void locationChanging(WebBrowserNavigationEvent e) {
                strNewResource = e.getNewResourceLocation();
                if (isAdminMode()) return;
                if (!isContentLegal()) {
//                    logger.info("запрещена ссылка: " + strNewResource);
                    e.consume();
                } else {
                    strLegalResource = "";
                    setFlMousePressed(false);
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
//                logger.info("запрошена ссылка: " + strNewResource);
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
        //переход только к разрешенному ресурсу в режиме блокировки контента
        if (
                isFlBlockContent() &&
                strNewResource.indexOf(strLegalResource, 0) == 0 &&
                strNewResource.length() == strLegalResource.trim().length()
                ) return true;
        //режим блокировки контента
        if (isFlBlockContent()) return false;
        //разрешен контент в рамках правил DOMAIN
        if (strNewResource.indexOf(urlMain, 0) == 0) return true;
        //переход на ссылку Youtube в режиме блокировки контента
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
                        setFlBlockContent(true);
                        strLegalResource = strNewResource;
                        logger.info("setFlBlockContent(true)");
                        webBrowser.navigate(strNewResource);
                    }
                    return false;
                }
            }
        }
        return false;
    }

}