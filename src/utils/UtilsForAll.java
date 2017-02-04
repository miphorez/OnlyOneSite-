package utils;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import net.sf.image4j.codec.ico.ICODecoder;
import org.apache.commons.io.FileUtils;
import xml.XMLSettingsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.logging.*;
import java.util.logging.Formatter;
import java.util.prefs.Preferences;

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
        if (DEBUG) {
            utils.UtilsForAll.setLoggerConsoleHandler(logger);
        } else {
            if (!utils.UtilsForAll.setLoggerFileHandler(logger)) {
                JOptionPane.showMessageDialog(null, "Ошибка доступа к настройкам логгера");
                return null;
            }
        }
        logger.fine("Logger Ok!");

        XMLSettingsUtils xmlSettingsUtils = new XMLSettingsUtils(logger);
        if (!xmlSettingsUtils.isFileExists()) {
            logger.info("Ошибка файла настроек программы");
            return null;
        }

        if (xmlSettingsUtils.isLogON()) {
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
        if (UtilsForAll.createDirectoryInProgramData(DIRECTORY_PROGRAMDATA) == null) return null;
        return UtilsForAll.createDirectoryInProgramData(DIRECTORY_PROGRAMDATA_LOG);
    }

    public static String getFileNameXMLParams() {
        File fileParams = createDirForXMLParams();
        if (fileParams == null) return "";
        String strFileName = fileParams.getAbsolutePath();
        strFileName += "\\" + FILE_XML_PARAMS;
        return strFileName;
    }

    public static String getFileNameTemp(String strFileName) {
        return getTempDir() + "/" + strFileName;
    }

    public static File getTempDir() {
        return UtilsForAll.getTempDirectory(DIRECTORY_PROGRAMDATA);
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
        return UtilsForAll.createDirectoryInProgramData(DIRECTORY_PROGRAMDATA);
    }

    private static File createDirForXMLParams() {
        if (UtilsForAll.createDirectoryInProgramData(DIRECTORY_PROGRAMDATA) == null) return null;
        return UtilsForAll.createDirectoryInProgramData(DIRECTORY_PROGRAMDATA_SET);
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

    public static String strCodeBase64(String str) {
        byte[] bytes = new byte[0];
        try {
            bytes = str.getBytes("cp1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            bytes = str.getBytes();
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String strDecodeBase64(String str) {
        String result;
        try {
            result = new String(Base64.getDecoder().decode(str), "Windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result = new String(Base64.getDecoder().decode(str));
        }
        return result;
    }

    public static boolean copyFileFromResource(String strResource, String strFileName) {
        URL resURL = getMainClass().getResource(strResource);
        File dest = new File(strFileName);
        try {
            FileUtils.copyURLToFile(resURL, dest);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void setCustomIconForProgram(JFrame frame) {
        ArrayList<Image> images = new ArrayList<>();
        ImageIcon icon = new ImageIcon(getMainClass().getResource(ICO_PNG_16));
        images.add(icon.getImage());
        icon = new ImageIcon(getMainClass().getResource(ICO_PNG_32));
        images.add(icon.getImage());
        icon = new ImageIcon(getMainClass().getResource(ICO_PNG_48));
        images.add(icon.getImage());
        icon = new ImageIcon(getMainClass().getResource(ICO_PNG_64));
        images.add(icon.getImage());
        frame.setIconImages(images);
    }

    private static BufferedImage makeBIM(Image image, int w, int h){
        BufferedImage bim = new BufferedImage(
                w,
                h,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bim.createGraphics();
        g.drawImage(image,0,0,null);
        g.dispose();
        return bim;
    }

    public static void setCustomIconForProgram_(JFrame frame) {
        String strImgInTemp = getFileNameResourceImgInTemp(MAIN_WINDOW_ICON);
        File file = new File(strImgInTemp);
        if (!file.exists()) return;
        java.util.List<BufferedImage> images = null;
        try {
            images = ICODecoder.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setIconImages(images);
    }

    public static String getFileNameResourceImgInTemp(String strImgName) {
        File file = new File(getFileNameTemp(strImgName));
        if (!file.exists())
            if (!copyFileFromResource(RES_IMG+strImgName, getFileNameTemp(strImgName))) return "";
        return getFileNameTemp(strImgName);
    }

    public static String getPrefString(String prefName, String presetStr) {
        Preferences prefs = Preferences.userRoot();
        String iStr = prefs.get(prefName, presetStr);
        if (Objects.equals(iStr, "")) iStr = presetStr;
        return iStr;
    }

    public static boolean goDialogYesNo(String strTitle, String strQuestion) {
        URL resURL = utils.UtilsForAll.getMainClass().getResource(ICO_PNG_48);
        Object[] strTitleBtn = {"Да", "Нет"};
        int yesno = JOptionPane.showOptionDialog(null,
                strQuestion,
                strTitle,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon(resURL),
                strTitleBtn,
                strTitleBtn[1]);
        return (yesno == 0);
    }

    public static String getMD5String(String iStr) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        messageDigest.reset();
        messageDigest.update(iStr.getBytes());
        byte[] digest = messageDigest.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
    }
}
