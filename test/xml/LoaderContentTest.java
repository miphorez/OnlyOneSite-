package xml;

import org.junit.Test;
import utils.ConstantForAll;
import xml.preset.TContent;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static utils.ConstantForAll.OOS_PNG_32;

public class LoaderContentTest {
    @Test
    public void getListTContent() throws Exception {
        List<String> optionList = new ArrayList<String>();
        ArrayList<TContent> listTContent = new XMLSettingsUtils(null).getContentListFromXMLSettings();
        for (TContent tContent: listTContent) {
            System.out.println(tContent);
            optionList.add(tContent.getName());
        }
        Object[] options = optionList.toArray();
        URL resURL = utils.UtilsForAll.getMainClass().getResource(OOS_PNG_32);
        Object ob = JOptionPane.showInputDialog(
                null,
                "Сделайте выбор из списка",
                "Выбор сайта для просмотра",
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(resURL),
                options,
                options[0]);
        JOptionPane.showMessageDialog(null, ob);
    }

}