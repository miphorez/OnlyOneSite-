package xml;

import org.w3c.dom.*;
import xml.preset.TContent;

import java.util.Objects;
import java.util.logging.Logger;

import static utils.ConstantForAll.NODE_CONTENT;
import static utils.ConstantForAll.NODE_ROOT;
import static utils.UtilsForAll.strToXorHexData;

public class XMLSettingsAdd extends XMLSettings {

    public XMLSettingsAdd(Logger logger) {
        super(logger);
    }

    public boolean go(TContent newContent) {
        logger.info("Добавление контента в содержание:");
        return addContentToFileSettings(newContent) && saveSettingsFile();
    }

    private boolean addContentToFileSettings(TContent newContent) {
        Document doc = getDocXMLSettings();

        NodeList nodeList = getNodeList(doc.getElementsByTagName(NODE_ROOT));
        if (nodeList == null) return false;
        Element rootElement = (Element) nodeList.item(0);

        Element content = doc.createElement(NODE_CONTENT);
        content.setAttribute("id", newContent.getStrId());
        content.setAttribute("name", strToXorHexData(newContent.getName()));
        content.setAttribute("link", strToXorHexData(newContent.getLink()));
        content.setAttribute("type", strToXorHexData(newContent.getType().name()));
        content.setAttribute("modeDel", newContent.getModeDel());
        rootElement.appendChild(content);

        return true;
    }
}
