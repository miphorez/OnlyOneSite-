package content.messenger;

import xml.preset.ETContent;

public class ArgForMessenger {
    public String strLinkContent;
    public String strNameContent;
    public String strTypeContent = ETContent.LINK_NONE.name();

    public ArgForMessenger(String strLinkContent, String strNameContent, String strTypeContent) {
        this.strLinkContent = strLinkContent;
        this.strNameContent = strNameContent;
        this.strTypeContent = strTypeContent;
    }

    public ArgForMessenger(ArgForMessenger arg) {
        strLinkContent = arg.strLinkContent;
        strNameContent = arg.strNameContent;
        strTypeContent = arg.strTypeContent;
    }
}
