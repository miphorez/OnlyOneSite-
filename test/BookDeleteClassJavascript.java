import chrriis.common.UIUtils;
import chrriis.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class BookDeleteClassJavascript {

    protected static final String LS = System.getProperty("line.separator");

    public static JComponent createContent() {
        JPanel contentPane = new JPanel(new BorderLayout());
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        webBrowserPanel.setBorder(BorderFactory.createTitledBorder("Native Web Browser component"));
        final JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.setBarsVisible(true);
        webBrowser.setStatusBarVisible(true);
        webBrowser.navigate("http://www.online-life.cc/");
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        contentPane.add(webBrowserPanel, BorderLayout.CENTER);
        JPanel configurationPanel = new JPanel(new BorderLayout());
        configurationPanel.setBorder(BorderFactory.createTitledBorder("Configuration"));
        final JTextArea configurationTextArea = new JTextArea(
                "var list = document.getElementsByTagName('noindex');\n" +
                        "\n" +
                        "while(list[0]) {\n" +
                        "    list[0].parentNode.removeChild(list[0]);\n" +
                        "}" +
//                "var element = document.getElementById(\"messageArea\");\n" +
//                        "element.parentNode.removeChild(element);" +
                        LS);
        JScrollPane scrollPane = new JScrollPane(configurationTextArea);
        Dimension preferredSize = scrollPane.getPreferredSize();
        preferredSize.height += 20;
        scrollPane.setPreferredSize(preferredSize);
        configurationPanel.add(scrollPane, BorderLayout.CENTER);
        JPanel configurationButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        JButton executeJavascriptButton = new JButton("Execute Javascript");
        executeJavascriptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                webBrowser.executeJavascript(configurationTextArea.getText());
            }
        });
        configurationButtonPanel.add(executeJavascriptButton);
        JCheckBox enableJavascriptCheckBox = new JCheckBox("Enable Javascript", true);
        enableJavascriptCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                webBrowser.setJavascriptEnabled(e.getStateChange() == ItemEvent.SELECTED);
                // Javascript state only affects subsequent pages. Let's reload the content.
//                webBrowser.setHTMLContent(htmlContent);
            }
        });
        configurationButtonPanel.add(enableJavascriptCheckBox);
        configurationPanel.add(configurationButtonPanel, BorderLayout.SOUTH);
        contentPane.add(configurationPanel, BorderLayout.NORTH);
        return contentPane;
    }

    /* Standard main method to try that test as a standalone application. */
    public static void main(String[] args) {
        NativeInterface.open();
        UIUtils.setPreferredLookAndFeel();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("DJ Native Swing Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(createContent(), BorderLayout.CENTER);
                frame.setSize(1200, 800);
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
        NativeInterface.runEventPump();
    }
}
