package utils;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static utils.ConstantForAll.*;
import static utils.UtilsForAll.*;

public class UtilsForAllTest {

    @Test
    public void substrLinkTest() throws Exception {
        String str = "\\http://www.gnu.org/licenses/\\";
        str = str.substring(1, str.length() - 1);
        System.out.print(str);
    }

    @Test
    public void openLinkTest() throws Exception {
        if(Desktop.isDesktopSupported())
        {
            Desktop.getDesktop().browse(new URI("http://www.example.com"));
        }
    }

    @Test
    public void isContentLegalTest() throws Exception {
//    https://www.youtube.com/watch?v=K-yTrFDHpbw
        String strNewResource = "http://www.youtube.com/embed/9hWBOVesZQA?list=PLXnIohISHNIvsnUNe8RwvhksUSFzdQsiT";
        int pos = strNewResource.indexOf("?list=");
        if (pos != -1){
            strNewResource = strNewResource.substring(0,pos);
        }
        System.out.println(strNewResource);
        pos = strNewResource.indexOf("http:");
        if (pos != -1){
            strNewResource = strNewResource.replaceAll("http:","https:");
        }
        System.out.println(strNewResource);
        pos = strNewResource.indexOf("embed/");
        if (pos != -1){
            strNewResource = strNewResource.replaceAll("embed/","watch?v=");
        }
        System.out.println(strNewResource);
    }

    @Test
    public void copyFileFromResorceTest2() throws Exception {
        String MAIN_WINDOW_ICON = "/res/img/oos.ico";
        assertTrue(copyFileFromResource(MAIN_WINDOW_ICON, getFileNameTemp("oos.ico")));
    }

    @Test
    public void copyFileFromResorceTest() throws Exception {
        String MAIN_WINDOW_ICON = "/res/img/oos.ico";
        URL resURL = getMainClass().getResource(MAIN_WINDOW_ICON);
        File dest = new File(getFileNameTemp("oos.ico"));
        FileUtils.copyURLToFile(resURL, dest);
    }

    @Test
    public void strToBase64Test() throws Exception {
        String encoded = strCodeBase64("Hello and Привет!");
        System.out.println(encoded);
        String decoded = strDecodeBase64(encoded);
        System.out.println(decoded);
    }

    @Test
    public void getMD5StringTest() throws Exception {
        System.out.println(getMD5String("12345"));
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
//        Document doc = Jsoup.connect(urlContent).get();
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