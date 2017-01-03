package content;

import chrriis.dj.nativeswing.swtimpl.components.*;
import content.messenger.Messenger;
import frm.about.FrmAboutAndVersion;
import frm.password.FrmPassword;
import xml.LoaderContent;
import xml.preset.TContent;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public abstract class DecoratorContent {
    private boolean flAdmin = false;
    Logger logger;
    JWebBrowser webBrowser;
    private JPanel webBrowserPanel;
    String strNewResource;
    Content_ONLINELIFE.BookmarkContent bookmarkContent;
    private TContent tContent;
    private Messenger messenger;
    private FrmPassword frmPassword;

    DecoratorContent(Logger logger, TContent tContent, Messenger messenger) {
        this.logger = logger;
        this.tContent = tContent;
        this.messenger = messenger;
        frmPassword = new FrmPassword(logger);

        new Thread(() -> {
            while (true){
                Thread.yield();
                if (flAdmin != frmPassword.getResult()) {
                    flAdmin = frmPassword.getResult();
                    showAdminMode();
                }
            }
        }).start();
    }

    public JComponent createContent() {
        JPanel contentPane = new JPanel(new BorderLayout());
        webBrowserPanel = new JPanel(new BorderLayout());
        webBrowserPanel.setBorder(BorderFactory.createTitledBorder(tContent.getName()));

        webBrowser = new JWebBrowser() {
            @Override
            protected WebBrowserDecorator createWebBrowserDecorator(Component renderingComponent) {
                return createCustomWebBrowserDecorator(this, renderingComponent);
            }
        };
        webBrowser.setMenuBarVisible(false);
        webBrowser.setLocationBarVisible(false);
        webBrowser.setStatusBarVisible(true);
        webBrowser.setButtonBarVisible(true);

        webBrowser.navigate(tContent.getLink());
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        contentPane.add(webBrowserPanel, BorderLayout.CENTER);

        itemContent();
        return contentPane;
    }

    public abstract void itemContent();

    abstract boolean isContentLegal();

    boolean isAdminMode() {
        return flAdmin;
    }

    private WebBrowserDecorator createCustomWebBrowserDecorator(JWebBrowser webBrowser, Component renderingComponent) {
        return new DefaultWebBrowserDecorator(webBrowser, renderingComponent) {
            @Override
            protected void addMenuBarComponents(WebBrowserMenuBar menuBar) {
                super.addMenuBarComponents(menuBar);
                JMenu myMenu = new JMenu("Содержание");
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
                btnContent.addActionListener(e ->
                        LoaderContent.getInstance(logger, messenger).selectItemContent()
                );
                buttonBar.add(btnContent);
                final JButton btnAddContent = new JButton("[Добавить]");
                btnAddContent.addActionListener(e ->
                        showAccessToAdminMode()
                );
                buttonBar.add(btnAddContent);
                final JButton btnAbout = new JButton("[О программе]");
                btnAbout.addActionListener(e ->
                        showFrmAbout()
                );
                buttonBar.add(btnAbout);
            }
        };
    }

    private void showAccessToAdminMode() {
        switchOverAdminMode();
    }

    private void showAdminMode() {
        if (flAdmin) {
            webBrowserPanel.setBorder(BorderFactory.createTitledBorder(tContent.getName() + " [admin mode]"));
            webBrowser.setMenuBarVisible(true);
        }else {
            webBrowserPanel.setBorder(BorderFactory.createTitledBorder(tContent.getName()));
            webBrowser.setMenuBarVisible(false);
            webBrowser.setLocationBarVisible(false);
        }
    }

    private void switchOverAdminMode(){
        frmPassword.go();
        new Thread(() -> {
            while (frmPassword.isFlVisibleFrm()){
                Thread.yield();
            }
        }).start();
    }

    private void showFrmAbout() {
        new FrmAboutAndVersion();
    }

    public TContent getTContent() {
        return tContent;
    }

}
