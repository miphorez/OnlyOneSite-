package xml;

import org.w3c.dom.*;
import utils.ConstantForAll;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

import static utils.ConstantForAll.NODE_CONTENT;
import static utils.ConstantForAll.NODE_ROOT;
import static utils.UtilsForAll.getFileNameTemp;
import static utils.UtilsForAll.getFileNameXMLParams;

class XMLSettingsUpdate {
    private Logger logger;

    XMLSettingsUpdate(Logger logger) {
        this.logger = logger;
    }

    boolean go() {
        logger.info("Апдейт файла настроек:");

        if (!new XMLSettingsCreate(logger).go(getFileNameTemp())) return false;
        logger.info("-> записан временный файл с новыми настройками");

        if (!moveParamsToTempFileSettings()) return false;
        logger.info("-> перенесены старые параметры в новый файл настроек");

        return (new File(getFileNameTemp())).delete();
    }

    private boolean moveParamsToTempFileSettings() {
        XMLSettings xmlSettingsOld = new XMLSettings(logger);
        Document docXMLSettingsOld = xmlSettingsOld.getDocXMLSettings();
        XMLSettings xmlSettingsNew = new XMLSettings(logger);
        xmlSettingsNew.setFileName(getFileNameTemp());
        Document docXMLSettingsNew = xmlSettingsNew.getDocXMLSettings();

        transferNodeAttr("", docXMLSettingsOld.getFirstChild(), docXMLSettingsNew);
        xmlSettingsNew.setRootAttr("Ver", ConstantForAll.PROGRAM_VERSION);
        return !saveNewSettinsFile(docXMLSettingsNew, getFileNameXMLParams());
    }

    private boolean saveNewSettinsFile(Document docXMLSettingsNew, String fileName) {
        StreamResult result = new StreamResult(new File(fileName));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            logger.info(e.getMessage());
            return true;
        }
        DOMSource source = new DOMSource(docXMLSettingsNew);
        if (transformer != null) {
            try {
                transformer.transform(source, result);
            } catch (TransformerException e) {
                logger.info(e.getMessage());
                return true;
            }
        }
        return false;
    }

    private static void transferNodeAttr(String strContentName, Node nodeOld, Document docNew) {

        while ((nodeOld.getNodeType() == Node.COMMENT_NODE)) nodeOld = nodeOld.getNextSibling();

        String text = nodeOld.getNodeValue();
        if (text != null && !text.isEmpty() && (!text.contains("\n"))) {
            System.out.println(" value = \"" + text + "\"");
        }

        NamedNodeMap attributes = nodeOld.getAttributes();
        if (attributes != null) {
            if (Objects.equals(nodeOld.getNodeName(), NODE_CONTENT)) {
                strContentName = getContentName(nodeOld, attributes);
            }
            transferAttrToNewSettinsByContentName(docNew, strContentName, nodeOld, attributes);
        }

        NodeList children = nodeOld.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            transferNodeAttr(strContentName, children.item(i), docNew);
        }
    }

    private static void transferAttrToNewSettinsByContentName(Document doc,
                                                              String strContentName,
                                                              Node node,
                                                              NamedNodeMap attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            setAttrItemContentNode(doc,
                    strContentName,
                    node.getNodeName(),
                    attr.getNodeName(),
                    attr.getTextContent());
        }
    }

    private static boolean setAttrItemContentNode(Document doc,
                                                  String strContentName,
                                                  String strNodeName,
                                                  String strAttr, String strValue) {
        if (Objects.equals(strContentName, "")) {
            //базовые атрибуты файла установок
            return setRootAttr(doc, strAttr, strValue);
        }

        NodeList nodeList = getNodeList(doc.getElementsByTagName(NODE_CONTENT));
        if (nodeList == null) return false;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            if (Objects.equals(node.getAttribute("name"), strContentName)) {
                if (Objects.equals(strNodeName, NODE_CONTENT)) {
                    node.setAttribute(strAttr, strValue);
                    return true;
                }
                nodeList = node.getElementsByTagName(strNodeName);
                if (nodeList == null) return false;
                for (int j = 0; j < nodeList.getLength(); j++) {
                    ((Element) nodeList.item(j)).setAttribute(strAttr, strValue);
                }
                return true;
            }
        }
        return false;
    }

    private static boolean setRootAttr(Document doc, String strAttr, String strValue) {
        NodeList entries = doc.getElementsByTagName(NODE_ROOT);

        int num;
        if (entries != null) {
            num = entries.getLength();
        } else return false;

        if (num == 0) return false;
        Element node = (Element) entries.item(0);
        node.setAttribute(strAttr, strValue);
        return true;
    }

    private static NodeList getNodeList(NodeList itemNodeList) {
        int num;
        if (itemNodeList == null) return null;
        num = itemNodeList.getLength();
        if (num == 0) return null;
        return itemNodeList;
    }

    private static String getContentName(Node node, NamedNodeMap attributes) {
        String strContentName = null;
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            if (Objects.equals(node.getNodeName(), NODE_CONTENT) &&
                    Objects.equals(attr.getNodeName(), "name")) {
                strContentName = attr.getTextContent();
                break;
            }
        }
        return strContentName;
    }
}
