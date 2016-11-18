package content;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Objects;
import java.util.logging.Logger;

import static utils.UtilsForAll.getFileNameHTMLContent;

public class ModifyContent {
    private Logger logger;
    private String strNewResource;

    public ModifyContent(Logger logger, String strNewResource) {
        this.logger = logger;
        this.strNewResource = strNewResource;
    }

    private boolean saveAndModifyContentInFile() {
        String strContent;
        try {
            Document doc = Jsoup.connect(strNewResource).get();
            if (saveContent(doc.toString(), getFileNameHTMLContent())) {
                strContent = contentModify(getFileNameHTMLContent());
                if (Objects.equals(strContent, "")) {
                    logger.info("ошибка модификации контента");
                    return false;
                }
            } else {
                logger.info("ошибка записи контента в файл: " + getFileNameHTMLContent());
            }
        } catch (IOException e) {
            logger.info("ошибка загрузки контента: " + e.getMessage());
        }

        logger.info("загрузка модифицированного контента");
        return true;
    }

    private String contentModify(String fileNameHTMLContent) {
        logger.info("модификация контента");
        File input = new File(fileNameHTMLContent);
        Document doc;
        try {
            doc = Jsoup.parse(input, "cp1251", "");
            Element element = doc.getElementById("dle-content");
            if (element != null) {
                Document docNew = Jsoup.parse(element.html());

                docNew.getElementById("mc-container").remove();

                removeElementByClass(docNew, "post-title");
                removeElementByClass(docNew, "related-block");
                removeElementByTag(docNew, "noindex");
                removeElementByClass(docNew, "pull-left", "rating");
                saveContent(docNew.html(), getFileNameHTMLContent());
                return docNew.html();
            }
        } catch (IOException e) {
            logger.info("ошибка парсинка контента: " + e.getMessage());
        }
        return "";
    }

    private void removeElementByTag(Document doc, String strElement) {
        Elements elements = doc.getElementsByTag(strElement);
        elements.forEach(Node::remove);
    }

    private void removeElementByClass(Document doc, String strElement) {
        Elements elements = doc.getElementsByClass(strElement);
        elements.forEach(Node::remove);
    }

    private void removeElementByClass(Document doc, String strElement1, String strElement2) {
        Elements elements = doc.getElementsByClass(strElement1);
        for (Element e : elements) {
            Elements esInside1 = e.getElementsByClass(strElement2);
            if (esInside1 != null) {
                esInside1.remove();
            }
        }
    }

    private boolean saveContent(String strForSave, String strFileName) {
        try {
            OutputStream out = new FileOutputStream(strFileName);
            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(out, "cp1251"));
            fileWriter.write(strForSave, 0, strForSave.length());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            logger.info("ошибка записи контента в файл: " + e.getMessage());
            return false;
        }
        return true;
    }
}
