package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import xml.preset.TContent;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

import static utils.ConstantForAll.NODE_CONTENT;
import static utils.ConstantForAll.NODE_ROOT;
import static utils.UtilsForAll.*;

public class XMLSettings {
    public Logger logger;
    private Document docXMLSettings;
    private String fileName;
    private DocumentBuilder db;

    XMLSettings(Logger logger) {
        this.logger = logger;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);

        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.info("ошибка XMLSettings: " + e.getMessage());
            return;
        }

        setFileName(getFileNameXMLParams());
    }

    boolean setFileName(String fileName){
        this.fileName = fileName;
        try {
            docXMLSettings = db.parse(new FileInputStream(new File(fileName)));
        } catch (SAXException | IOException e) {
            logger.info("ошибка XMLSettings.setFileName: " + e.getMessage());
            docXMLSettings = null;
            return false;
        }
        return true;
    }

    String getAttr(String strName, String strAttr, boolean modeCript) {
        String iAttr = "";

        NodeList entries;
        if (docXMLSettings != null) {
            entries = strName == null ?
                    docXMLSettings.getElementsByTagName(NODE_ROOT) :
                    docXMLSettings.getElementsByTagName(NODE_CONTENT);
        } else return iAttr;

        int num;
        if (entries != null) {
            num = entries.getLength();
        } else return iAttr;

        if (num == 0) return iAttr;
        if (strName == null){
            //атрибуты NODE_ROOT
            Element node = (Element) entries.item(0);
            return node.getAttribute(strAttr);
        }else{
            //атрибуты NODE_CONTENT по ключевому имени
            for (int i = 0; i < num; i++) {
                Element node = (Element) entries.item(i);
                if (node.hasAttribute(strAttr)) {
                    String strDecriptAttr = modeCript ? strDecodeBase64(node.getAttribute("name")) : node.getAttribute("name");
                    if (Objects.equals(strDecriptAttr, strName)) {
                        return modeCript ? strDecodeBase64(node.getAttribute(strAttr)) : node.getAttribute(strAttr);
                    }
                }
            }
        }

        return iAttr;
    }

    boolean setRootAttr(String strAttr, String strValue) {
        NodeList entries = docXMLSettings.getElementsByTagName(NODE_ROOT);

        int num;
        if (entries != null) {
            num = entries.getLength();
        } else return false;

        if (num == 0) return false;
        Element node = (Element) entries.item(0);
        node.setAttribute(strAttr, strValue);
        return true;
    }

    boolean setUpdateItemContent(TContent tContent) {
               return setAllAttrItemContent(tContent) && saveSettingsFile();
    }

    boolean delItemContent(TContent tContent) {
               return delNodeItemContent(tContent) && saveSettingsFile();
    }

    private boolean delNodeItemContent(TContent tContent) {
        NodeList listRoot = getNodeList(docXMLSettings.getElementsByTagName(NODE_ROOT));
        Element nodeRoot = (Element) listRoot.item(0);
        NodeList nodeList = getNodeList(docXMLSettings.getElementsByTagName(NODE_CONTENT));
        if (nodeList == null) return false;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            if (Objects.equals(node.getAttribute("id"), tContent.getStrId())) {
                nodeRoot.removeChild(node);
                return true;
            }
        }
        return false;
    }

    private boolean setAllAttrItemContent(TContent tContent) {
        NodeList nodeList = getNodeList(docXMLSettings.getElementsByTagName(NODE_CONTENT));
        if (nodeList == null) return false;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            if (Objects.equals(node.getAttribute("id"), tContent.getStrId())) {
                node.setAttribute("id", tContent.getStrId());
                node.setAttribute("name", strCodeBase64(tContent.getName()));
                node.setAttribute("link", strCodeBase64(tContent.getLink()));
                node.setAttribute("type", strCodeBase64(tContent.getType().name()));
                node.setAttribute("modeDel", tContent.getModeDel());
                return true;
            }
        }
        return false;
    }

    boolean saveSettingsFile() {
        StreamResult result = new StreamResult(new File(fileName));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            logger.info(e.getMessage());
            return false;
        }
        DOMSource source = new DOMSource(docXMLSettings);
        if (transformer != null) {
            try {
                transformer.transform(source, result);
            } catch (TransformerException e) {
                logger.info(e.getMessage());
                return false;
            }
        }
        return true;
    }

    Document getDocXMLSettings() {
        return docXMLSettings;
    }

    NodeList getNodeList(NodeList itemNodeList) {
        int num;
        if (itemNodeList == null) return null;
        num = itemNodeList.getLength();
        if (num == 0) return null;
        return itemNodeList;
    }
}
