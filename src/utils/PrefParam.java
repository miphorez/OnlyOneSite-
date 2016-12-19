package utils;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class PrefParam {
    private static JFrame frameMain;

    public static int posXMainWindow;      //позиция Х - левый верхний угол программы
    public static int posYMainWindow;      //позиция Y - левый верхний угол программы
    public static int widthMainWindow;     //ширина окна программы
    public static int hightMainWindow;     //высота окна программы

    public static final String PREF_Password = "PassDev";
    public static String PRESET_Password = "12345";

    public PrefParam(JFrame frame) {
        frameMain = frame;
    }

    public void loadPosAndSizeMain() {
        Preferences prefs = Preferences.userRoot();
        posXMainWindow = prefs.getInt("posXMainWindow", 0);
        posYMainWindow = prefs.getInt("posYMainWindow", 0);
        widthMainWindow = prefs.getInt("widthMainWindow", 800);
        hightMainWindow = prefs.getInt("hightMainWindow", 600);
    }

    static void savePosAndSizeMain(){
        Rectangle frameBounds = frameMain.getBounds();
        Preferences prefs = Preferences.userRoot();
        prefs.putInt("posXMainWindow", frameBounds.x);
        prefs.putInt("posYMainWindow", frameBounds.y);
        prefs.putInt("widthMainWindow", frameBounds.width);
        prefs.putInt("hightMainWindow", frameBounds.height);
    }
}

