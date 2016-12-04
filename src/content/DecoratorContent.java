package content;

import chrriis.dj.nativeswing.swtimpl.components.*;
import content.messenger.Messenger;
import xml.LoaderContent;
import xml.preset.TContent;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.logging.Logger;

import static utils.ConstantForAll.*;

public abstract class DecoratorContent {
    boolean flAdmin = false;
    Logger logger;
    JWebBrowser webBrowser;
    private JPanel webBrowserPanel;
    String strNewResource;
    Content_ONLINELIFE.BookmarkContent bookmarkContent;
    private TContent tContent;
    private Messenger messenger;

    DecoratorContent(Logger logger, TContent tContent, Messenger messenger) {
        this.logger = logger;
        this.tContent = tContent;
        this.messenger = messenger;
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
        webBrowser.setBarsVisible(false);
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

    boolean checkAdminMode() {
        if (Objects.equals(strNewResource, urlAdminPass)) {
            flAdmin = !flAdmin;
            if (flAdmin) {
                webBrowserPanel.setBorder(BorderFactory.createTitledBorder(tContent.getName() + " [admin mode]"));
            } else {
                webBrowserPanel.setBorder(BorderFactory.createTitledBorder(tContent.getName()));
            }
            return true;
        }
        return false;
    }

    private WebBrowserDecorator createCustomWebBrowserDecorator(JWebBrowser webBrowser, Component renderingComponent) {
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
                btnContent.addActionListener(e ->
                        LoaderContent.getInstance(logger, messenger).selectItemContent()
                );
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

    public TContent getTContent() {
        return tContent;
    }

}
