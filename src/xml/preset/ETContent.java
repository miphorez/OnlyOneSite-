package xml.preset;

import java.util.Objects;

public enum ETContent {
    LINK_NONE,

    LINK_ONLINELIFE,
    LINK_HTML,
    LINK_DOMAIN,
    ;

    public static ETContent getByStrType(String type) {
        for (ETContent etContent:values()) {
            if (Objects.equals(etContent.name(), type)) return etContent;
        }
        return null;
    }
}
