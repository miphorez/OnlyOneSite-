package xml;

import content.messenger.ArgForMessenger;
import content.messenger.Messenger;
import content.messenger.MessengerChangeContent;
import xml.preset.TContent;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static utils.ConstantForAll.OOS_PNG_32;

public class LoaderContent {
    private static Logger logger;
    private static LoaderContent ourInstance;
    private ArrayList<TContent> listTContent;
    private static MessengerChangeContent messengerContent = new MessengerChangeContent();

    public static LoaderContent getInstance(Logger log, Messenger messenger) {
        logger = log;
        if (ourInstance == null) {
            ourInstance = new LoaderContent();
        }
        messengerContent.addObserver(messenger.getListener());
        return ourInstance;
    }

    private void goLoadListTContent() {
        logger.info("Загрузка контента...");
        listTContent = new XMLSettingsUtils(logger).getContentListFromXMLSettings();
        if (listTContent != null) {
            logger.info("Количество ссылок: " + listTContent.size());
        }
    }

    public TContent selectItemContent(){
        List<String> optionList = new ArrayList<>();
        goLoadListTContent();
        for (TContent tContent: listTContent) {
            optionList.add(tContent.getName());
        }
        Object[] options = optionList.toArray();
        URL resURL = utils.UtilsForAll.getMainClass().getResource(OOS_PNG_32);
        Object strNameContent = JOptionPane.showInputDialog(
                null,
                "Сделайте выбор из списка",
                "Выбор сайта для просмотра",
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(resURL),
                options,
                options[0]);
        TContent tContent = TContent.getTContentByName(listTContent, (String) strNameContent);
        if (tContent != null) {
            ArgForMessenger arg = new ArgForMessenger(
                    tContent.getLink(),
                    tContent.getName(),
                    tContent.getType().name()
            );
            messengerContent.putNewContent(arg);
        }
        return tContent;
    }
}
