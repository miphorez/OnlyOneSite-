package content;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

public class BookmarkContent {
    private Logger logger;
    private String htmlSource;
    private ArrayList<String> listLegalURL = new ArrayList<>();

    public BookmarkContent(Logger logger, String htmlSource) {
        this.logger = logger;
        this.htmlSource = htmlSource;
        createListLegalURL();
    }

    private void createListLegalURL() {
        Document doc = Jsoup.parse(htmlSource);

        Elements elements = doc.getElementsByClass("custom-poster");
        for (Element e : elements) {
            Elements esInside1 = e.getElementsByTag("a");
            if (esInside1 != null) {
                listLegalURL.add(esInside1.attr("href"));
            }
        }
    }

    public void typeList() {
        if (listLegalURL.size() != 0) {
            logger.info("список легального контента:");
            for (String strItem : listLegalURL) {
                logger.info(strItem);
            }
        } else logger.info("список легального контента пуст");
    }

    public boolean saveContent(String strForSave, String strFileName) {
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

    boolean isResourseInListLegalURL(String strNewResource) {
        for (String strItem : listLegalURL) {
            if (Objects.equals(strItem, strNewResource)) return true;
        }
        return false;
    }
}
