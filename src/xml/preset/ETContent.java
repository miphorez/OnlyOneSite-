package xml.preset;

import javax.swing.*;
import java.util.Objects;

public enum ETContent {
    LINK_NONE(false),
    LINK_HTML(true),
    LINK_DOMAIN(true),
    LINK_ONLINELIFE(false),
    LINK_ONLINEMULTFILMY(false),
    ;

    private boolean flView;

    ETContent(boolean flView) {
        this.flView = flView;
    }

    public static ETContent getByStrType(String type) {
        for (ETContent etContent:values()) {
            if (Objects.equals(etContent.name(), type)) return etContent;
        }
        return null;
    }

    public static ComboBoxModel<ETContent> createListTypes() {
        DefaultComboBoxModel<ETContent> cbmType = new DefaultComboBoxModel<>();
        for (ETContent etContent:values()) {
            if (etContent.isFlView()) cbmType.addElement(etContent);
        }
        return cbmType;
    }

    public boolean isFlView() {
        return flView;
    }
}
