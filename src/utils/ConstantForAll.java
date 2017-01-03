package utils;

public class ConstantForAll {
    public static String PROGRAM_TITLE = "Only One Site";
    public static String PROGRAM_VERSION = "1.1.2";
    static final String MODULE_SIGN = "OnlyOneSite";

    public static final String LS = System.getProperty("line.separator");

    //логгер
    static String LOG_FILENAME = MODULE_SIGN + ".log";

    //настройки
    public static String NODE_ROOT = "ProgramSettings";
    public static String NODE_CONTENT = "ItemContent";

    //файлы программы
    public static String FILE_HTML_CONTENT = "content.html";
    public static String FILE_XML_PARAMS = MODULE_SIGN + ".xml";
    public static String FILE_XML_PARAMS_TEMP = "temp.xml";

    //директория программы
    public static String DIRECTORY_PROGRAMDATA = MODULE_SIGN;
    public static String DIRECTORY_PROGRAMDATA_SET = MODULE_SIGN + "\\Settings";
    public static String DIRECTORY_PROGRAMDATA_LOG = MODULE_SIGN + "\\Log";

    //изображения
    public static String OOS_PNG_16 = "/res/img/oos_16.png";
    public static String OOS_PNG_32 = "/res/img/oos_32.png";
    public static String OOS_PNG_48 = "/res/img/oos_48.png";
    public static String OOS_PNG_64 = "/res/img/oos_64.png";
}
