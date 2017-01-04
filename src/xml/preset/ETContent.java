package xml.preset;

import javax.swing.*;
import java.util.Objects;

public enum ETContent {
    LINK_NONE,
    LINK_HTML,
    LINK_DOMAIN,
    LINK_ONLINELIFE,
    ;

    public static ETContent getByStrType(String type) {
        for (ETContent etContent:values()) {
            if (Objects.equals(etContent.name(), type)) return etContent;
        }
        return null;
    }

    public static ComboBoxModel<ETContent> createListTypes() {
        DefaultComboBoxModel<ETContent> cbmType = new DefaultComboBoxModel<>();
        for (ETContent etContent:values()) {
            if (!Objects.equals(etContent.name(), "LINK_NONE")) cbmType.addElement(etContent);
        }
        return cbmType;
    }
}
