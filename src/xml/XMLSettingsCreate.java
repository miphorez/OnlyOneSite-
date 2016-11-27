package xml;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import xml.preset.PresetContent;
import xml.preset.TContent;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import static utils.ConstantForAll.NODE_CONTENT;
import static utils.ConstantForAll.NODE_ROOT;
import static utils.UtilsForAll.strToXorHexData;


class XMLSettingsCreate extends XMLSettings{

    XMLSettingsCreate(Logger logger) {
        super(logger);
    }

    boolean go(String strFileNameSettings) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(createXMLSettingsParams(new PresetContent().getListPresetContent()));

            StreamResult result = new StreamResult(new File(strFileNameSettings));

            if (transformer != null) {
                transformer.transform(source, result);
            }else {
                logger.info("ошибка открытия файла настроек программы");
                return false;
            }

        } catch (ParserConfigurationException | TransformerException pce) {
            logger.info("ошибка создания файла настроек: "+ pce.getMessage());
            return false;
        }
        return true;
    }

    private Document createXMLSettingsParams(ArrayList<TContent> listContent) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();

        Comment comment = doc.createComment(
                "аргумент \"Log\" - ведение log-файла (1 - включено, 0 - отключено)" );
        Element rootElement = doc.createElement(NODE_ROOT);
        rootElement.setAttribute("Log", "1");
        rootElement.setAttribute("Mode", "777");
        rootElement.setAttribute("Ver", utils.ConstantForAll.PROGRAM_VERSION);
        doc.appendChild(rootElement);
        rootElement.getParentNode().insertBefore(comment, rootElement);
        comment = doc.createComment(
                "аргумент \"link\" - URL ссылка на контент");
        rootElement.appendChild(comment);
        comment = doc.createComment(
                "аргумент \"name\" - наименование для списка");
        rootElement.appendChild(comment);
        comment = doc.createComment(
                "аргумент \"type\" - тип контента");
        rootElement.appendChild(comment);
        comment = doc.createComment(
                "аргумент \"modeDel\" - возможность удаления контента из списка (0 - не удалять, 1 - можно удалять)");
        rootElement.appendChild(comment);

        for (TContent tContent: listContent) {
            Element script = doc.createElement(NODE_CONTENT);
            script.setAttribute("name", strToXorHexData(tContent.getName()));
            script.setAttribute("link", strToXorHexData(tContent.getLink()));
            script.setAttribute("type", strToXorHexData(tContent.getType().name()));
            script.setAttribute("modeDel", tContent.getModeDel());
            rootElement.appendChild(script);
        }

        return doc;
    }
}
