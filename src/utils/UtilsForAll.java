package utils;

import xml.XMLProgramSettings;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.*;

import static utils.ConstantForAll.*;

public class UtilsForAll {

    public static Logger setupLogger() {

        Logger logger = Logger.getLogger(utils.UtilsForAll.getMainClass().getName());
        if (!utils.UtilsForAll.setLoggerFileHandler(logger)) {
            System.out.println("Ошибка настройки логгера");
            return null;
        }
        //log выводится только в консоль
        utils.UtilsForAll.setLoggerConsoleHandler(logger);

        XMLProgramSettings xmlProgramSettings = new XMLProgramSettings(logger);
        if (!xmlProgramSettings.isXMLSettingsFile()){
            System.out.println("Ошибка файла настроек программы");
            return null;
        }

        if (xmlProgramSettings.isLogInSettings()){
            logger.setLevel(Level.INFO);
        } else logger.setLevel(Level.OFF);

        return logger;
    }

    private static Class getMainClass() {
        return main.Main.class;
    }

    public static void exitFromProgram() {
        PrefParam.savePosAndSizeMain();
        System.exit(0);
    }

    private static File createDirForLog() {
        if (UtilsForAll.createDirectoryInProgramData(DIRECTORY_USER_PROG)==null) return null;
        return UtilsForAll.createDirectoryInProgramData(DIRECTORY_USER_PROG_LOG);
    }

    public static String getFileNameXMLParams() {
        File fileParams = createDirForXMLParams();
        if (fileParams == null) return "";
        String strFileName = fileParams.getAbsolutePath();
        strFileName += "\\" + FILE_XML_PARAMS;
        return strFileName;
    }

    public static String getFileNameHTMLContent() {
        File fileParams = createDirForHTMLContent();
        if (fileParams == null) return "";
        String strFileName = fileParams.getAbsolutePath();
        strFileName += "\\" + FILE_HTML_CONTENT;
        return strFileName;
    }

    private static File createDirForHTMLContent() {
        return UtilsForAll.createDirectoryInProgramData(DIRECTORY_USER_PROG);
    }

    private static File createDirForXMLParams() {
        if (UtilsForAll.createDirectoryInProgramData(DIRECTORY_USER_PROG)==null) return null;
        return UtilsForAll.createDirectoryInProgramData(DIRECTORY_USER_PROG_SET);
    }

    private static File createDirectoryInProgramData(String strDirName) {
        String strDir = utils.UtilsForAll.getUserDirectoryProgramData(strDirName);
        final File fileDir = new File(strDir);
        if (!fileDir.exists())
            if (!fileDir.mkdir()) return null;
        return fileDir;
    }

    private static String getUserDirectoryProgramData(String strDirName) {
        return getDirectoryProgramData() + "\\" + strDirName;
    }

    private static String getDirectoryProgramData() {
        return System.getenv("PROGRAMDATA");
    }

    static boolean setLoggerConsoleHandler(Logger logger) {
        //удалить все хэндлерсы для логгера
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
        //добавить новый
        ConsoleHandler fh;
        try {
            fh = new ConsoleHandler();
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return String.format("%s: %s%n",
                            fh.getLevel().getLocalizedName(),
                            record.getMessage()
                    );
                }
            });
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
        } catch (SecurityException e) {
            logger.log(Level.SEVERE, "Exception: ", e);
            return false;
        }
        return true;
    }

    private static boolean setLoggerFileHandler(Logger logger) {
        //удалить все хэндлерсы для логгера
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
        //добавить новый
        FileHandler fh;
        File fileLog = createDirForLog();
        if (fileLog == null) return false;
        String logFileName = fileLog.getAbsolutePath();
        logFileName += "\\" + LOG_FILENAME;
        try {
            fh = new FileHandler(logFileName, false);
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    Date iDate = java.sql.Timestamp.valueOf(LocalDateTime.now());
                    String itemDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(iDate);
                    return String.format("[%s] %s: %s%n",
                            itemDateStr,
                            fh.getLevel().getName(),
                            record.getMessage()
                    );
                }
            });

            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
        } catch (SecurityException | IOException e) {
            logger.log(Level.SEVERE, "Exception: ", e);
            return false;
        }
        return true;
    }
}
