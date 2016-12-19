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
        new FrmPassword();
    }

}