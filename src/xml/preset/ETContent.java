package xml.preset;

import java.util.Objects;

public enum ETContent {
    ONLINE_LIFE,
    HTML_LINK,
    DOMEN_LINK,
    ;

    public static ETContent getByStrType(String type) {
        for (ETContent etContent:values()) {
            if (Objects.equals(etContent.name(), type)) return etContent;
        }
        return null;
    }
}
