package xml;

import org.junit.Test;
import xml.preset.TContent;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LoaderSettingsTest {
    @Test
    public void getListTContent() throws Exception {
        ArrayList<TContent> listTContent = new XMLSettingsUtils(null).getContentListFromXMLSettings();
        for (TContent tContent: listTContent) {
            System.out.println(tContent);
        }
    }

}