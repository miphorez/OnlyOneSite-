package utils;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import org.apache.commons.io.FileUtils;
import xml.XMLSettingsUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.*;

import static netscape.security.PrivilegeManager.TEMP_FILENAME;
import static utils.ConstantForAll.*;

public class UtilsForAll {

    public static ImageIcon createImageIcon(String strResImg) {
        URL resURL = utils.UtilsForAll.getMainClass().getResource(strResImg);
        if (resURL != null) {
            return new ImageIcon(resURL);
        } else {
            System.err.println("Couldn't find file: " + strResImg);
            return null;
        }
    }

    public static boolean setLookAndFeelForWindows() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "Ошибка setLookAndFeel: " + LS + e.getMessage());
            return false;
        }
        return true;
    }

    public static void setSwingForShowGUI(JFrame frame) {
        if (!setLookAndFeelForWindows()) return;
        //контролируем кнопку выхода из программы
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                utils.UtilsForAll.exitFromProgram();
            }
        });
    }

    public static void setupParamsForProgramm(Logger logger, JFrame frame) {
        logger.info("загрузка параметров программы");
        new PrefParam(frame).loadPosAndSizeMain();
    }

    public static void setPositionAndSizeMainWindow(Logger logger, JFrame frame) {
        logger.info("установка размера и положения окна программы");
        frame.setBounds(PrefParam.posXMainWindow,
                PrefParam.posYMainWindow,
                PrefParam.widthMainWindow,
                PrefParam.hightMainWindow);
        frame.setVisible(true);

        //центрируем по средине экрана при определенных условиях
        if ((PrefParam.posXMainWindow == 0) &&
                (PrefParam.posYMainWindow == 0))
            frame.setLocationRelativeTo(null);
    }

    public static Logger setupLogger() {

        Logger logger = Logger.getLogger(utils.UtilsForAll.getMainClass().getName());
        if (!utils.UtilsForAll.setLoggerFileHandler(logger)) {
            System.out.println("Ошибка настройки логгера");
            return null;
        }
        //log выводится только в консоль
//        utils.UtilsForAll.setLoggerConsoleHandler(logger);

        XMLSettingsUtils xmlSettingsUtils = new XMLSettingsUtils(logger);
        if (!xmlSettingsUtils.isFileExists()){
            logger.info("Ошибка файла настроек программы");
            return null;
        }

        if (xmlSettingsUtils.isLogON()){
            logger.setLevel(Level.INFO);
        } else logger.setLevel(Level.OFF);

        return logger;
    }

    public static Class getMainClass() {
        return main.Main.class;
    }

    public static void exitFromProgram() {
        PrefParam.savePosAndSizeMain();
        NativeInterface.close();
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

    public static String getFileNameTemp() {
        return getTempDir() + "/" + TEMP_FILENAME;
    }

    public static File getTempDir() {
        return UtilsForAll.getTempDirectory(DIRECTORY_USER_PROG);
    }

    public static File getTempDirectory(String strDirName) {
        final File dirTemp = new File(FileUtils.getTempDirectory().getAbsolutePath() + "/" + strDirName);
        if (Files.exists(Paths.get(dirTemp.toURI()), LinkOption.NOFOLLOW_LINKS)) return dirTemp;
        dirTemp.mkdir();
        return dirTemp;
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

    public static boolean setLoggerConsoleHandler(Logger logger) {
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

    public static String strToXorHexData(String str){
        String XOR_BinaryData = "sdaswan";
        String result = "";
        byte[] xorArr = XOR_BinaryData.getBytes();
        byte[] strArr = str.getBytes();
        int ii = 0;
        for (int i = 0; i < str.length(); i++) {
            byte b = (byte) (strArr[i] ^ xorArr[ii]);
            String hex = Integer.toHexString(b);
            if (hex.length() == 1) hex = "0" + hex;
            result += hex;
            ii++;
            if (ii == (XOR_BinaryData.length())) ii=0;
        }
        return result;
    }

    public static String xorHexDataToStr(String str){
        String XOR_BinaryData = "sdaswan";
        String result = "";
        String iStr = str;
        byte[] xorArr = XOR_BinaryData.getBytes();
        int ii = 0;
        while (iStr.length() > 0){
            String hs = iStr.substring(0,2);
            byte b = (byte) Integer.parseInt(hs, 16);
            b = (byte) (b ^ xorArr[ii]);
            char ch = (char)(b&0x00FF);
            result = result + String.valueOf(ch);
            ii++;
            if (ii == (XOR_BinaryData.length())) ii=0;
            iStr = iStr.substring(2);
        }
        return result;
    }
}
