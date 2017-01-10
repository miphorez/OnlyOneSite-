package frm.about;

import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class FrmAboutAndVersionTest {

    @Test
    public void goFrmAboutAndVersionTest() throws Exception {
        Logger logger = Logger.getLogger("test");
        if (!utils.UtilsForAll.setLoggerConsoleHandler(logger)) assertTrue(false);
        logger.info("логгер запущен");
        FrmAboutAndVersion frmAboutAndVersion = FrmAboutAndVersion.getInstance();
        while (frmAboutAndVersion.isFlVisibleFrm()){
            Thread.yield();
        }
    }
}