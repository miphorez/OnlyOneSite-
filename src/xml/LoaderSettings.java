package xml;

import xml.preset.TContent;

import java.util.ArrayList;
import java.util.logging.Logger;

public class LoaderSettings {
    private static Logger logger;
    private static LoaderSettings ourInstance;
    private ArrayList<TContent> listTContent;

    public static LoaderSettings getInstance(Logger log) {
        logger = log;
        if (ourInstance == null) {
            ourInstance = new LoaderSettings();
        }
        return ourInstance;
    }

    private LoaderSettings() {
        goLoadListTContent();
    }

    private void goLoadListTContent() {
        logger.info("Загрузка контента...");
        listTContent = new XMLSettingsUtils(logger).getContentListFromXMLSettings();
        if (listTContent != null) {
            logger.info("Количество ссылок: " + listTContent.size());
        }
    }

    public ArrayList<TContent> getListTContent() {
        return listTContent;
    }
}
