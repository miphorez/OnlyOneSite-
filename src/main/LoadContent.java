package main;

import content.*;
import content.messenger.Messenger;
import main.listener.ListenerFromContent;
import xml.LoaderContent;
import xml.preset.TContent;

import javax.swing.*;
import java.util.logging.Logger;

public class LoadContent implements Messenger {
    private Logger logger;
    private JFrame frame;
    private ListenerFromContent listenerFromContent;

    LoadContent(Logger logger, JFrame frame) {
        this.logger = logger;
        this.frame = frame;
    }

    @Override
    public ListenerFromContent getListener() {
        return listenerFromContent;
    }

    void go() {
        listenerFromContent = new ListenerChangeContent(logger, frame, this);
        TContent tContent = LoaderContent.getInstance(logger, this).selectItemContent();
        if (tContent == null) {
            logger.info("программа закрыта: не выбран контент");
            System.exit(-2);
        }
    }
}
