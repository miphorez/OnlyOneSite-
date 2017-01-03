package utils;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

import static utils.ConstantForAll.MODULE_SIGN;

public class PrefParam {
    private static JFrame frameMain;

    public static int posXMainWindow;      //позиция Х - левый верхний угол программы
    public static int posYMainWindow;      //позиция Y - левый верхний угол программы
    public static int widthMainWindow;     //ширина окна программы
    public static int hightMainWindow;     //высота окна программы

    public static final String PREF_PASS = MODULE_SIGN + "PassForAdminMode";
    public static final String PREF_PASS_PRESET = "12345";

    public PrefParam(JFrame frame) {
        frameMain = frame;
    }

    public void loadPosAndSizeMain() {
        Preferences prefs = Preferences.userRoot();
        posXMainWindow = prefs.getInt(MODULE_SIGN + "posXMainWindow", 0);
        posYMainWindow = prefs.getInt(MODULE_SIGN + "posYMainWindow", 0);
        widthMainWindow = prefs.getInt(MODULE_SIGN + "widthMainWindow", 800);
        hightMainWindow = prefs.getInt(MODULE_SIGN + "hightMainWindow", 600);
    }

    static void savePosAndSizeMain() {
        Rectangle frameBounds = frameMain.getBounds();
        Preferences prefs = Preferences.userRoot();
        prefs.putInt(MODULE_SIGN + "posXMainWindow", frameBounds.x);
        prefs.putInt(MODULE_SIGN + "posYMainWindow", frameBounds.y);
        prefs.putInt(MODULE_SIGN + "widthMainWindow", frameBounds.width);
        prefs.putInt(MODULE_SIGN + "hightMainWindow", frameBounds.height);
    }
}

