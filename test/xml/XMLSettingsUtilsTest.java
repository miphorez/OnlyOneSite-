package xml;

import org.junit.Test;

public class XMLSettingsUtilsTest {
    @Test
    public void getAttr() throws Exception {
        System.out.println(new XMLSettings(null).getAttr(null, "Mode", false));
        System.out.println(new XMLSettings(null).getAttr("wikipedia", "link", true));
    }

}