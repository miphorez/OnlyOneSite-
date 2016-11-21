package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static utils.ConstantForAll.*;

public class UtilsForAllTest {

    @Test
    public void strResTest() throws Exception {
        String str = "c:\\Source\\-=Java=-\\OnlyOneSite\\src\\res\\page\\page1.html";
        URL resURL = utils.UtilsForAll.getMainClass().getResource(RES_PAGE_CONTENT);
        System.out.println(resURL.toString());
        System.out.println(resURL.getPath());
        System.out.println(resURL.getFile());
        System.out.println(utils.UtilsForAll.getMainClass().getResource(RES_PAGE_CONTENT).toURI().getPath());
//        System.out.println(utils.UtilsForAll.getMainClass().getResource(str));

//        System.out.println(utils.UtilsForAll.getMainClass().getResource("/res/page/page1.html").getFile());
//        System.out.println(utils.UtilsForAll.getMainClass().getResource("/res/page/page1.html").getPath());
    }

    @Test
    public void exampleJSoupTest() throws Exception {
        String baseHtml = "<div id='stylized' class='myform'>"
                + "<input id='txt_question' name='preg' type='text' disabled='disabled' style='width:150px;'>"
                + "<div id='detail_question'>Rock</div></div>";

        Document doc = Jsoup.parse(baseHtml);
//        doc.getElementById("detail_question").remove();
//        Elements elements = doc.select("input");
        Element element = doc.getElementById("stylized");
        Document docNew = Jsoup.parse(element.html());
        System.out.println(docNew.html());
    }

    @Test
    public void loadContentTest() throws Exception {
        Logger logger = Logger.getLogger("test");
        if (!utils.UtilsForAll.setLoggerConsoleHandler(logger)) assertTrue(false);
        logger.info("логгер запущен");
        Document doc = Jsoup.connect(urlContent).get();
//        System.out.println(doc.toString());
//        assertTrue(saveContent(logger, doc.toString(), getFileNameHTMLContent()));
    }

    @Test
    public void removeDivTest() throws Exception {
        Logger logger = Logger.getLogger("test");
        if (!utils.UtilsForAll.setLoggerConsoleHandler(logger)) assertTrue(false);
        logger.info("логгер запущен");

        File input = new File("c:\\ProgramData\\OnlyOneSite\\content_base.html");
        Document doc = Jsoup.parse(input, "cp1251", "");

//        Element element = doc.getElementById("main-container");
//        Elements elements = doc.select("div");
//        for (Element e: elements) {
//            System.out.println(e.toString());
//        }
        Element element = doc.getElementById("dle-content");
        if (element != null) {
            Document docNew = Jsoup.parse(element.html());
            docNew.getElementById("mc-container").remove();
//            System.out.println(docNew.html());
            Elements elements = docNew.getElementsByClass("post-title");
            for (Element e : elements) {
                e.remove();
            }
            elements = docNew.getElementsByClass("related-block");
            for (Element e : elements) {
                e.remove();
            }
            elements = docNew.getElementsByTag("noindex");
            for (Element e : elements) {
                e.remove();
            }
            elements = docNew.getElementsByClass("pull-left");
            for (Element e : elements) {
                Elements esInside1 = e.getElementsByClass("rating");
                if (esInside1 != null) {
                    esInside1.remove();
                }
            }
//            elements = docNew.getElementsByClass("pull-right");
//            for (Element e : elements) {
//                System.out.println(e.toString());
//                System.out.println("---------------------------------");
//            }
            System.out.println(docNew.html());
        }
//        saveContent(logger, strNew, getFileNameHTMLContent());

//        String baseHtml = "<div id='stylized' class='myform'>"
//                + "<input id='txt_question' name='preg' type='text' disabled='disabled' style='width:150px;'>"
//                + "<div id='detail_question'>Rock</div></div>";
//
//        Document doc = Jsoup.parse(baseHtml);
//        doc.getElementById("mc-container").remove();
//        Elements elements = doc.select("div");
//        System.out.println(elements);
    }
}