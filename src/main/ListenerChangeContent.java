package main;

import content.Content_DOMAIN;
import content.Content_HTML;
import content.Content_ONLINELIFE;
import content.messenger.ArgForMessenger;
import content.messenger.Messenger;
import main.listener.ListenerFromContent;
import xml.preset.TContent;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class ListenerChangeContent extends ListenerFromContent implements Observer {
    private Logger logger;
    private JFrame frame;
    private Messenger messenger;

    ListenerChangeContent(Logger logger, JFrame frame, Messenger messenger) {
        this.logger = logger;
        this.frame = frame;
        this.messenger = messenger;
    }

    public void update(Observable o, Object arg) {
        ArgForMessenger struct = new ArgForMessenger((ArgForMessenger) arg);
        TContent tContent = new TContent(
                struct.strLinkContent,
                struct.strNameContent,
                struct.strTypeContent);
        execChangeContent(tContent);
    }

    private void execChangeContent(TContent tContent) {
        frame.getContentPane().removeAll();
        switch (tContent.getType()) {
            case LINK_HTML:
                Content_HTML contentHtml = new Content_HTML(logger, tContent, messenger);
                frame.getContentPane().add(
                        contentHtml.createContent(),
                        BorderLayout.CENTER);
                break;
            case LINK_DOMAIN:
                Content_DOMAIN contentDomain = new Content_DOMAIN(logger, tContent, messenger);
                frame.getContentPane().add(
                        contentDomain.createContent(),
                        BorderLayout.CENTER);
                break;
            case LINK_ONLINELIFE:
                Content_ONLINELIFE contentOnlinelife = new Content_ONLINELIFE(logger, tContent, messenger);
                frame.getContentPane().add(
                        contentOnlinelife.createContent(),
                        BorderLayout.CENTER);
        }
    }
}
