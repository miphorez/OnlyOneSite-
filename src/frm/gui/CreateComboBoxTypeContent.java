package frm.gui;

import xml.preset.ETContent;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CreateComboBoxTypeContent extends CreateComboBox {

    public CreateComboBoxTypeContent(int sizeX, int sizeY) {
        super(sizeX, sizeY);
        setModel(ETContent.createListTypes());
    }

    public void setSelectedType(ETContent type) {
        for (int i = 0; i < getItemCount(); i++) {
            if (Objects.equals(((ETContent) getItemAt(i)).name(), type.name())){
                setSelectedIndex(i);
                return;
            }
        }
        setSelectedIndex(0);
    }
}
