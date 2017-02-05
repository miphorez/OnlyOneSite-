package content;

import chrriis.dj.nativeswing.swtimpl.components.*;
import content.messenger.Messenger;
import frm.about.FrmAboutAndVersion;
import frm.addcontent.FrmAddContent;
import frm.password.FrmPassword;
import xml.LoaderContent;
import xml.XMLSettingsAdd;
import xml.XMLSettingsUtils;
import xml.preset.TContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.logging.Logger;

import static utils.ConstantForAll.DEBUG;
import static utils.UtilsForAll.goDialogYesNo;

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
    private boolean flMousePressed;
    private boolean flBlockContent;

    DecoratorContent(Logger logger, TContent tContent, Messenger messenger) {
        this.logger = logger;
        this.tContent = tContent;
        this.messenger = messenger;
        flAdmin = tContent.getModeAdmin();
        frmPassword = FrmPassword.getInstance(logger, flAdmin);
        flMousePressed = false;
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

            @Override
            public void navigateBack() {
                flBlockContent = false;
                logger.info("setFlBlockContent(false)");
                super.navigateBack();
            }
        };

        webBrowser.getNativeComponent().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                flMousePressed = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    flMousePressed = false;
                }).start();
                super.mouseReleased(e);
            }
        });

        flAdmin = frmPassword.isFlAdminMode();
        if (DEBUG) {
            webBrowser.setBarsVisible(true);
            flAdmin = true;
        } else if (flAdmin) {
            webBrowserPanel.setBorder(BorderFactory.createTitledBorder(tContent.getName() + " [родительский режим]"));
            webBrowser.setMenuBarVisible(true);
        } else {
            webBrowser.setLocationBarVisible(false);
            webBrowser.setMenuBarVisible(false);
            webBrowser.setStatusBarVisible(true);
            webBrowser.setButtonBarVisible(true);
        }
        webBrowser.navigate(tContent.getLink());
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        contentPane.add(webBrowserPanel, BorderLayout.CENTER);

        new Thread(() -> {
            while (true) {
                Thread.yield();
                if (flAdmin != frmPassword.getResult()) {
                    flAdmin = frmPassword.getResult();
                    frmPassword.setFlAdminMode(flAdmin);
                    showAdminMode();
                }
            }
        }).start();

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
                JMenu myMenu = new JMenu("Операции над ресурсом");
                JMenuItem menuItem = new JMenuItem("Добавить текущий ресурс");
                menuItem.addActionListener(e -> showFrmAddContent(webBrowser.getResourceLocation()));
                myMenu.add(menuItem);
                menuItem = new JMenuItem("Редактировать параметры ресурса");
                menuItem.addActionListener(e -> showFrmAddContent(""));
                myMenu.add(menuItem);
                menuItem = new JMenuItem("Удалить ресурс из содержания");
                menuItem.addActionListener(e -> delThisContent());
                myMenu.add(menuItem);

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
                        LoaderContent.getInstance(logger, messenger, flAdmin).selectItemContent()
                );
                buttonBar.add(btnContent);
                final JButton btnAddContent = new JButton("[Вход в родительский режим]");
                btnAddContent.addActionListener(e ->
                        switchOverAdminMode()
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

    private void delThisContent() {
        if (tContent.isModeDel()) {
            if (goDialogYesNo("Подтверждение операции",
                    "Вы действительно хотите удалить \nтекущий ресурс из содержания?"))
                new XMLSettingsUtils(logger).delContent(tContent);
        } else
            JOptionPane.showMessageDialog(null, "Нельзя удалить ресурс \nиз базового списка");
    }

    private void showAdminMode() {
        if (flAdmin) {
            webBrowserPanel.setBorder(BorderFactory.createTitledBorder(tContent.getName() + " [родительский режим]"));
            webBrowser.setMenuBarVisible(true);
        } else {
            webBrowserPanel.setBorder(BorderFactory.createTitledBorder(tContent.getName()));
            webBrowser.setMenuBarVisible(false);
            webBrowser.setLocationBarVisible(false);
        }
    }

    private void switchOverAdminMode() {
        frmPassword.go();
        new Thread(() -> {
            while (frmPassword.isFlVisibleFrm()) {
                Thread.yield();
            }
        }).start();
    }

    private void showFrmAbout() {
        FrmAboutAndVersion.getInstance(messenger);
    }

    private void showFrmAddContent(String strNewLink) {
        FrmAddContent frmAddContent = FrmAddContent.getInstance(logger);
        boolean flEditContent = false;
        if (Objects.equals(strNewLink, "")) {
            flEditContent = true;
            frmAddContent.goUpdate(tContent);
        } else {
            frmAddContent.go(strNewLink);
        }
        boolean finalFlEditContent = flEditContent;
        new Thread(() -> {
            while (frmAddContent.isFlVisibleFrm()) {
                Thread.yield();
            }
            TContent newContent = frmAddContent.getResultContent();
            if (newContent != null) {
                if (finalFlEditContent) {
                    newContent.setId(tContent.getId());
                    newContent.setModeDel(tContent.isModeDel());
                    new XMLSettingsUtils(logger).setUpdateContent(newContent);
                } else {
                    new XMLSettingsAdd(logger).go(newContent);
                }
            }
        }).start();
    }

    public TContent getTContent() {
        return tContent;
    }

    public void setFlMousePressed(boolean flMousePressed) {
        this.flMousePressed = flMousePressed;
    }

    public boolean isFlMousePressed() {
        return flMousePressed;
    }

    public boolean isFlBlockContent() {
        return flBlockContent;
    }

    public void setFlBlockContent(boolean flBlockContent) {
        this.flBlockContent = flBlockContent;
    }
}
