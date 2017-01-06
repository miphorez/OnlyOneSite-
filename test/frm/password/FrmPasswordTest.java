package frm.password;

import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class FrmPasswordTest {

    @Test
    public void loadContentTest() throws Exception {
        Logger logger = Logger.getLogger("test");
        if (!utils.UtilsForAll.setLoggerConsoleHandler(logger)) assertTrue(false);
        logger.info("логгер запущен");
        FrmPassword frmPassword = new FrmPassword(logger, false);
        frmPassword.go();
        while (frmPassword.isFlVisibleFrm()){
            Thread.yield();
        }
        assertTrue(frmPassword.getResult());
    }

}