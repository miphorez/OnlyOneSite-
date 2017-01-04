package frm.addcontent;

import frm.password.FrmPassword;
import org.junit.Test;
import xml.XMLSettingsAdd;
import xml.preset.ETContent;
import xml.preset.TContent;

import java.util.logging.Logger;

import static org.junit.Assert.*;
import static utils.UtilsForAll.setLookAndFeelForWindows;

public class FrmAddContentTest {

    @Test
    public void frmAddContentTest() throws Exception {
        Logger logger = Logger.getLogger("test");
        if (!utils.UtilsForAll.setLoggerConsoleHandler(logger)) assertTrue(false);
        logger.info("логгер запущен");
        setLookAndFeelForWindows();
        FrmAddContent frmAddContent = new FrmAddContent(logger);
        frmAddContent.go("12345");
        while (frmAddContent.isFlVisibleFrm()) {
            Thread.yield();
        }
        System.out.println(frmAddContent.getResultContent());
    }

    @Test
    public void xmlAddContentTest() throws Exception {
        Logger logger = Logger.getLogger("test");
        if (!utils.UtilsForAll.setLoggerConsoleHandler(logger)) assertTrue(false);
        logger.info("логгер запущен");
        TContent tContent = new TContent(
                "new link",
                "new name",
                ETContent.LINK_DOMAIN.name()
        );
        assertTrue(new XMLSettingsAdd(logger).go(tContent));
    }

}