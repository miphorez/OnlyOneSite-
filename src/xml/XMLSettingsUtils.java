package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import xml.preset.TContent;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

import static utils.ConstantForAll.NODE_CONTENT;
import static utils.UtilsForAll.*;

public class XMLSettingsUtils extends XMLSettings {

    public XMLSettingsUtils(Logger logger) {
        super(logger);
    }

    public boolean isFileExists() {
        String fileNameSettings = getFileNameXMLParams();
        if (!Files.exists(Paths.get(fileNameSettings), LinkOption.NOFOLLOW_LINKS)) {
            return new XMLSettingsCreate(logger).go(getFileNameXMLParams()) && Files.exists(Paths.get(fileNameSettings), LinkOption.NOFOLLOW_LINKS);
        }
        return new ControlVersion(logger).go();
    }

    public boolean isLogON() {
        return Objects.equals(getAttr(null, "Log", false), "1");
    }

    public ArrayList<TContent> getContentListFromXMLSettings() {
        ArrayList<TContent> arrayList = new ArrayList<>();
        Document doc = getDocXMLSettings();

        NodeList entries;
        if (doc != null) {
            entries = doc.getElementsByTagName(NODE_CONTENT);
        } else return null;

        int num;
        if (entries != null) {
            num = entries.getLength();
        } else return arrayList;
        if (num == 0) return arrayList;

        for (int i = 0; i < num; i++) {
            Element node = (Element) entries.item(i);
            arrayList.add(new TContent(
                    node.getAttribute("link"),
                    node.getAttribute("name"),
                    node.getAttribute("type"),
                    node.getAttribute("modeDel")));
        }
        return arrayList;
    }
}
