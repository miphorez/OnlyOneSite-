package content;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import javax.swing.*;
import java.io.File;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static utils.ConstantForAll.*;

public class BookmarkContentONLINELIFETest {


    @Test
    public void loadContentTest() throws Exception {
        Logger logger = Logger.getLogger("test");
        if (!utils.UtilsForAll.setLoggerConsoleHandler(logger)) assertTrue(false);
        logger.info("логгер запущен");

        File input = new File("c:\\ProgramData\\OnlyOneSite\\content.html");
        Document doc = Jsoup.parse(input, "cp1251", "");

        Elements elements = doc.getElementsByClass("custom-poster");
        for (Element e : elements) {
            Elements esInside1 = e.getElementsByTag("a");
            if (esInside1 != null) {

                System.out.println(esInside1.attr("href"));
                System.out.println("-------------------------");
            }
        }
//        System.out.println(doc.toString());
    }
}